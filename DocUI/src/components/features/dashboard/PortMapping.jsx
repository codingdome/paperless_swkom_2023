import React from "react";
import PORTS from "../../../data/ports";

const Port = ({ descr, port }) => {
    return (
        <div className="flex flex-col items-center gap-2 bg-[#1c1d27] p-2 rounded-[10px]">
            <p className="font-bold text-sm">{descr}</p>
            <p className="font-bold">{port}</p>
        </div>
    )
}

const PortMapping = () => {
    return (
        <div className="w-full flex flex-col gap-2">

            <p className="font-bold">Port Mapping</p>

            <div className="grid grid-cols-2 md:grid-cols-4 lg:grid-cols-7 gap-4">
                {PORTS.map((item, index) => (
                    <Port key={index} descr={item.DESCRIPTION} port={item.PORT} />
                ))}
            </div>
        </div>


    )
}

export default PortMapping;