import React from "react";
import './Loader.css';

const Loader = () => {
    return (
        <div className="w-full flex flex-col gap-3 items-center justify-center border-[2px] border-solid border-[#f3f3f4] border-opacity-10 p-4 rounded-[10px]">
            <p className="font-extrabold opacity-30">OCR LOADING</p>
            <div className="feeder">
                <div></div>
                <div></div>
                <div></div>
            </div>
        </div>
    )
}

export default Loader;