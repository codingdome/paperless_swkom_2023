import React, { useState, useEffect } from "react";

import Pie from "./CircleChart";

import { toast } from 'react-toastify';

const MinioStatus = () => {

    const [objectCount, setObjectCount] = useState(0);
    const [objectNames, setObjectNames] = useState([]);

    const deleteAllObjects = () => {
        fetch('http://localhost:8083/minio/delete-all-files', { method: 'DELETE' })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to delete objects');
                }

                toast.success('All files successfully deleted');
                fetchObjectCount();
                fetchObjectNames();
            })
            .catch(error => {
                toast.error('Error deleting files');
                console.error('Error:', error)
            });
    };

    const fetchObjectCount = () => {
        fetch('http://localhost:8083/minio/object-count')
            .then(response => response.json())
            .then(data => setObjectCount(data))
            .catch(error => console.error('Error:', error));
    };

    const fetchObjectNames = () => {
        fetch('http://localhost:8083/minio/object-names')
            .then(response => response.json())
            .then(data => {
                console.log('FileNames', data)
                setObjectNames(data)
            })
            .catch(error => console.error('Error:', error));
    };

    useEffect(() => {
        fetchObjectCount();
        fetchObjectNames();
    }, []);

    return (
        <div className="w-full h-full flex flex-col items-center gap-4 bg-[#1C1D26] p-4 rounded-[10px]">
            <h3 className="font-bold">MINIO STATUS</h3>

            <Pie percentage={objectCount} colour={'#9500ff'} />

            <div className="flex flex-col items-center gap-2  w-full rounded-[10px] p-4">
                <p>Items count</p>
                <p>{objectCount}</p>
                <button onClick={fetchObjectCount} className="bg-[#9500ff] px-4 py-2 rounded-full max-w-[150px] hover:bg-opacity-30 transition-all duration-300">Refresh Count</button>
            </div>

            <div className="w-full h-[2px] bg-[#15161d] rounded-full"></div>

            <div className="flex flex-col items-center gap-2  w-full rounded-[10px] p-4">
                <p>Files in sql db</p>
                <div className="flex flex-col gap-2 max-h-[110px] overflow-y-scroll">

                    {objectNames.map((fileName, index) => (
                        <div key={index} className="w-full flex items-center gap-2">
                            <p className="font-bold">{fileName}</p>
                        </div>
                    ))}
                </div>

                <button onClick={fetchObjectNames} className="bg-[#9500ff] px-4 py-2 rounded-full max-w-[150px] hover:bg-opacity-30 transition-all duration-300">Refresh Files</button>
            </div>

            <div className="w-full h-[2px] bg-[#15161d] rounded-full"></div>

            <div className="flex flex-col items-center gap-2  w-full rounded-[10px] p-4">
                <p>Delete all</p>
                <p>{objectCount}</p>
                <button onClick={deleteAllObjects} className="border-red-700 border-solid border-2 px-4 py-2 rounded-full max-w-[150px] hover:bg-red-700 transition-all duration-300">Delete All</button>
            </div>
        </div>
    )
}

export default MinioStatus;