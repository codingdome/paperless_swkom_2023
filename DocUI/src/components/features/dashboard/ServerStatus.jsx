import React, { useState, useEffect } from "react";
import Loader from "./Loader";

const ServerStatus = ({ url, name, info }) => {
    const [status, setStatus] = useState('Checking...');
    const [showInfo, setShowInfo] = useState(false);
    const [secondsSinceLastPing, setSecondsSinceLastPing] = useState(0);
    const [hasError, setHasError] = useState(false);

    const fetchStatus = () => {
        fetch(url)
            .then(response => {
                if (response.ok) {
                    setHasError(false);
                    return response.text();
                }
                throw new Error('Server not responding');
            })
            .then(text => {
                setStatus(text);
                setSecondsSinceLastPing(0); // Reset timer on successful ping
            })
            .catch(error => {
                setStatus('Server down');
                setHasError(true);
                setSecondsSinceLastPing(0); // Reset timer on failed ping
            });
    };

    useEffect(() => {
        fetchStatus(); // Initial ping
        const interval = setInterval(fetchStatus, 6000); // Ping every 5 seconds

        const timer = setInterval(() => {
            setSecondsSinceLastPing(seconds => seconds + 1);
        }, 1000); // Increment timer every second

        return () => {
            clearInterval(interval);
            clearInterval(timer); // Clean up timers
        };
    }, []);

    return (
        <div className="flex flex-col items-center gap-4 bg-[#1c1d27] p-4 rounded-[10px]">

            <div className="flex flex-col items-center gap-4">
                <h3 className="font-bold">{name}</h3>
                <p>{status}</p>

                <Loader hasError={hasError} />

            </div>


            <div className="w-full h-[2px] bg-[#15161d] rounded-full"></div>

            <div className="flex flex-col items-center">
                <p className="">Last Ping</p>
                <p> {secondsSinceLastPing} second{secondsSinceLastPing !== 1 ? 's' : ''} ago</p>
            </div>

            <div className="w-full h-[2px] bg-[#15161d] rounded-full"></div>

            {showInfo && <p>{info}</p>}

            <button
                onClick={() => setShowInfo(!showInfo)}
                className="underline"
            >
                {showInfo ? 'Hide Info' : 'Show Info'}
            </button>

        </div>
    );
};

export default ServerStatus;
