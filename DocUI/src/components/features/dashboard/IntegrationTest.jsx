import React, { useState, useEffect } from "react";
import LoaderPending from "./LoaderPending";

const Step = ({ number, name, status }) => {


    return (
        <div className={`bg-[#1c1d27] p-4 flex flex-col items-center gap-3 rounded-[10px] h-auto justify-between`}>
            <p className="font-bold text-2xl">{number}</p>
            <p className="font-bold text-center">{name}</p>
            {(status === "success") && (
                <div className="flex flex-col items-center gap-2">
                    <div className="rounded-full h-[20px] w-[20px] bg-[#00ff15]"></div>
                    <p className="font-bold opacity-50">success</p>
                </div>

            )}
            {(status === "pending") && (
                <div className="flex flex-col items-center gap-2">
                    <div className="w-full flex justify-center">
                        <LoaderPending />
                    </div>

                    <p className="font-bold opacity-50">pending...</p>
                </div>

            )}
            {(status === "failed") && (
                <div className="flex flex-col items-center gap-2">
                    <div className="rounded-full h-[20px] w-[20px] bg-[#f0005a]"></div>
                    <p className="font-bold opacity-50">failed</p>
                </div>

            )}
            {(status === "inactive") && (
                <div className="flex flex-col items-center gap-2">
                    <div className="rounded-full h-[20px] w-[20px] bg-gray-400"></div>
                    <p className="font-bold opacity-50">inactive</p>
                </div>

            )}
        </div>
    )
}

const IntegrationTest = () => {

    const initialSteps = [
        { number: 1, name: "Check Server Status", status: "inactive" },
        { number: 2, name: "Upload File", status: "inactive" },
        { number: 3, name: "Check Upload", status: "inactive" },
        { number: 4, name: "Check OCR result", status: "inactive" },
        { number: 5, name: "Delete File Status", status: "inactive" },
        { number: 6, name: "Check Deletion", status: "inactive" }
    ];

    const [steps, setSteps] = useState(initialSteps);
    const [currentStepIndex, setCurrentStepIndex] = useState(0);
    const [testRunning, setTestRunning] = useState(false);

    useEffect(() => {
        if (testRunning) {
            performStepTest(currentStepIndex);
        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [currentStepIndex, testRunning]);

    const performStepTest = async (index) => {
        if (index >= steps.length) {
            setTestRunning(false);
            return;
        }

        // Set the current test status to "pending"
        updateStepStatus(index, "pending");

        // Ensure each test takes at least 1 second
        const testWithDelay = async () => {
            await new Promise(resolve => setTimeout(resolve, 1500));
            switch (index) {
                case 0:
                    return await fetchServerStatus();
                case 1:
                    return await uploadFileTest();
                case 2:
                    return await checkUploadTest();
                case 3:
                    return await checkOCRResultTest();
                case 4:
                    return await deleteFileTest();
                case 5:
                    return await checkDeletionTest();
                default:
                    return false;
            }
        };

        const result = await testWithDelay();
        updateStepStatus(index, result ? "success" : "failed");
        setCurrentStepIndex(index + 1);
    };


    const updateStepStatus = (index, status) => {
        const newSteps = [...steps];
        newSteps[index].status = status;
        setSteps(newSteps);
    };

    const startIntegrationTest = () => {
        resetAllSteps();
        setTestRunning(true);
        setCurrentStepIndex(0);
    };


    //Check STEP 1 // SERVER STATUS
    // Function to fetch server status for multiple URLs
    const fetchServerStatus = async () => {
        const urls = ["http://localhost:8081/health", "http://localhost:8082/health", "http://localhost:8083/health"]; // Replace with actual URLs
        try {
            const results = await Promise.all(urls.map(url => fetch(url)));
            return results.every(response => response.ok);
        } catch (error) {
            return false;
        }
    };

    const uploadFileTest = async () => {
        const filename = "TEST_FILE.pdf";
        const fileUrl = `${window.location.origin}/test/${filename}`;

        try {
            const response = await fetch(fileUrl);
            if (!response.ok) {
                throw new Error('Failed to download file');
            }

            const blob = await response.blob();
            const file = new File([blob], filename, { type: "application/pdf" });

            const formData = new FormData();
            formData.append('file', file);

            const uploadResponse = await fetch('http://localhost:8081/files/upload', {
                method: 'POST',
                body: formData,
            });

            if (!uploadResponse.ok) {
                throw new Error('Upload failed');
            }

            const uploadedFile = await uploadResponse.json();
            return uploadedFile.name === filename;
        } catch (error) {
            console.error('Error during file upload:', error);
            return false;
        }
    };




    //CHECK STEP 3 // CHECK UPLOAD
    const checkUploadTest = async () => {
        const filename = "TEST_FILE.pdf";

        try {
            const response = await fetch('http://localhost:8081/files/get/all');
            if (!response.ok) {
                throw new Error('Failed to fetch file list');
            }

            const files = await response.json();
            // Search for the filename in the fetched list
            const fileExists = files.some(file => file.name === filename);

            return fileExists;
        } catch (error) {
            console.error('Error fetching files:', error);
            return false;
        }
    };


    //CHECK STEP 4 // CHECK OCR RESULT
    const checkOCRResultTest = async () => {
        const filename = "TEST_FILE.pdf";
        const expectedOcrText = `Archive.org: Bietet ein in der Open Library mit über 230.000 Einträgen an. Auf diesem Portal werden auch Texte angeboten, die unter einer freien Lizenz (Creative Commons CC) stehen.`;

        console.log("Starting OCR result check for file:", filename);

        // Wait for 3 seconds
        console.log("Waiting for 3 seconds before checking...");
        await new Promise(resolve => setTimeout(resolve, 3000));

        try {
            console.log("Fetching file list from server...");
            const response = await fetch('http://localhost:8081/files/get/all');
            if (!response.ok) {
                throw new Error('Failed to fetch file list');
            }

            const files = await response.json();
            console.log("Received file list:", files);

            const file = files.find(f => f.name === filename);
            console.log("File found in list:", file);

            if (!file) {
                throw new Error('File not found in list');
            }

            console.log("Checking OCR extracted text...");

            // Remove whitespace from both texts for comparison
            const formatText = text => text.replace(/\s/g, "");
            const isOcrTextCorrect = formatText(file.ocrExtractedText) === formatText(expectedOcrText);

            console.log("Expected (formatted):", formatText(expectedOcrText));
            console.log("OCR Text (formatted):", formatText(file.ocrExtractedText));
            console.log("OCR Text is correct:", isOcrTextCorrect);

            return isOcrTextCorrect;
        } catch (error) {
            console.error('Error in OCR result check:', error);
            return false;
        }
    };




    //CHECK STEP 5 // CHECK DELETE FILE 
    const deleteFileTest = async () => {
        const filename = "TEST_FILE.pdf";

        console.log("Starting deletion test for file:", filename);

        try {
            console.log("Sending delete request for the file...");
            const response = await fetch(`http://localhost:8081/files/delete/${filename}`, {
                method: 'POST',
                // Include headers if needed (e.g., for authentication)
            });

            console.log("Received response for delete request:", response);

            if (!response.ok) {
                throw new Error('Failed to delete file');
            }

            return response.status === 200;
        } catch (error) {
            console.error('Error deleting file:', error);
            return false;
        }
    };


    // CHECK STEP 6 // CHECK DELETION
    const checkDeletionTest = async () => {
        const filename = "TEST_FILE.pdf";

        console.log("Starting deletion verification for file:", filename);

        try {
            console.log("Fetching file list from server...");
            const response = await fetch('http://localhost:8081/files/get/all');
            if (!response.ok) {
                throw new Error('Failed to fetch file list');
            }

            const files = await response.json();
            console.log("Received file list:", files);

            // Check that the filename is not in the fetched list
            const fileExists = files.some(file => file.name === filename);

            console.log("File exists after deletion:", fileExists);

            return !fileExists; // Return true if the file does NOT exist
        } catch (error) {
            console.error('Error in deletion verification:', error);
            return false;
        }
    };

    const resetAllSteps = () => {
        // Reset each step's status to "inactive"
        const resetSteps = steps.map(step => ({ ...step, status: "inactive" }));
        setSteps(resetSteps);

        // Reset the current step index and test running state
        setCurrentStepIndex(0);
        setTestRunning(false);
    };

    return (
        <div className="grid grid-cols-1 md:grid-cols-3 lg:grid-cols-6 gap-4">
            {steps.map((step) => (
                <Step key={step.number} number={step.number} name={step.name} status={step.status} />
            ))}
            <div className="col-span-1 md:col-span-3 lg:col-span-6">

                <div className="flex items-center gap-2">
                    <button
                        disabled={testRunning}
                        onClick={startIntegrationTest}
                        className={`${testRunning ? 'bg-gray-800' : 'bg-[#4661fc]'} px-6 py-2 rounded-full hover:bg-opacity-10 transition-all duration-300 font-bold`}>
                        {testRunning ? 'Running' : 'Start'}
                    </button>

                    <button
                        disabled={testRunning}
                        onClick={resetAllSteps}
                        className={`${testRunning ? 'bg-gray-800' : 'bg-[#1c1d27]'} px-6 py-2 rounded-full hover:bg-opacity-10 transition-all duration-300 font-bold`}>
                        Reset
                    </button>
                </div>

            </div>
        </div>
    );
}

export default IntegrationTest;