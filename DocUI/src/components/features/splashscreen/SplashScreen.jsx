import React from "react";
import Logo from "../../../assets/img/logo/logo.svg";
import Company from "../../../assets/img/logo/company.png";

import "./SplashScreen.css";

const SplashScreen = () => {
    return (
        <div className="bg-[#15161D] w-full h-full fixed top-0 left-0 flex flex-col items-center justify-center z-[30000] p-4 debug-3">
            <img className="w-full max-w-[200px] splash-logo" src={Logo} alt="Logo Doc" />


            <div className="flex items-center gap-4 fixed bottom-10">
                <p className="text-[#f3f3f4] font-bold">Developed by</p>
                <img className="w-[100px]" src={Company} alt="Siebenundzwanzigvier Development Logo" />
            </div>
        </div>
    )
}

export default SplashScreen;