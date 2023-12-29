import React, { useState, useEffect } from "react";
import { toast } from 'react-toastify';
import Pie from "./CircleChart";

const ElasticSearchStatus = () => {
    const [count, setCount] = useState(0);
    const [fileNames, setFileNames] = useState([]);

    const handleDeleteAll = () => {
        fetch('http://localhost:8083/elasticsearch/delete-all', { method: 'DELETE' })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                toast.success('All files successfully deleted');
                handleGetCount();
                handleGetAllFileNames();
            })
            .catch(error => {
                console.error('There was an error deleting the files:', error);
                toast.error('Error deleting files');
            });
    };

    const handleGetCount = () => {
        fetch('http://localhost:8083/elasticsearch/file-count')
            .then(response => response.json())
            .then(data => {
                setCount(data);
                console.log('Current Count:', data);
            })
            .catch(error => {
                console.error('There was an error fetching the count data:', error);
            });
    };

    const handleGetAllFileNames = () => {
        fetch('http://localhost:8083/elasticsearch/file-names')
            .then(response => response.json())
            .then(data => {
                setFileNames(data);
                console.log('File Names:', data);
            })
            .catch(error => {
                console.error('There was an error fetching the file names:', error);
            });
    };

    useEffect(() => {
        handleGetCount();
        handleGetAllFileNames();
    }, []);

    return (
        <div className="w-full h-full flex flex-col items-center gap-4 bg-[#1C1D26] p-4 rounded-[10px]">
            <h3 className="font-bold">ELASTIC SEARCH STATUS</h3>

            <Pie percentage={count} colour={'#ff0059'} />

            <div className="flex flex-col items-center gap-2  w-full rounded-[10px] p-4">
                <p>Items count</p>
                <p>{count}</p>
                <button onClick={handleGetCount} className="bg-[#ff0059] px-4 py-2 rounded-full max-w-[150px] hover:bg-opacity-30 transition-all duration-300">Refresh Count</button>
            </div>

            <div className="w-full h-[2px] bg-[#15161d] rounded-full"></div>

            <div className="flex flex-col items-center gap-2  w-full rounded-[10px] p-4">
                <p>Files in Elastic Search</p>
                <div className="flex flex-col gap-2 max-h-[110px] overflow-y-scroll">
                    {fileNames.map((fileName, index) => (
                        <div key={index} className="w-full flex items-center gap-2">
                            <p className="font-bold">{fileName}</p>
                        </div>
                    ))}
                </div>
                <button onClick={handleGetAllFileNames} className="bg-[#ff0059] px-4 py-2 rounded-full max-w-[150px] hover:bg-opacity-30 transition-all duration-300">Refresh Files</button>
            </div>

            <div className="w-full h-[2px] bg-[#15161d] rounded-full"></div>

            <div className="flex flex-col items-center gap-2  w-full rounded-[10px] p-4">
                <p>Delete all</p>
                <p>{count}</p>
                <button onClick={handleDeleteAll} className="border-red-700 border-solid border-2 px-4 py-2 rounded-full max-w-[150px] hover:bg-red-700 transition-all duration-300">Delete All</button>
            </div>
        </div>
    );
}

export default ElasticSearchStatus;
