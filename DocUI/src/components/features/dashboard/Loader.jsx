import React from "react";

import "./Loader.css";

const Loader = ({ hasError }) => {

    if (hasError) {
        return (
            <span className="loader-error"></span>
        )
    } else {
        return (
            <span className="loader-success"></span>
        )
    }

}

export default Loader;