import React from "react";

import SearchIcon from "../../../assets/img/icons/search.svg";

import './SearchInput.css';

const SearchInput = ({ searchText, setSearchText, handleSearchClick }) => {
    return (
        <div className="w-full flex gap-2 md:gap-4 items-center">
            <div className="input-icon-wrapper py-2">
                <input
                    type="text"
                    placeholder="Suche"
                    value={searchText}
                    onChange={(e) => setSearchText(e.target.value)}
                    className="py-2"
                />
                <img className="placeholder-icon w-[30px] h-[30px]" src={SearchIcon} alt="Search Icon" />
            </div>

            <button onClick={handleSearchClick}>
                <div className="bg-[#0520ff] rounded-full py-3 px-4">
                    <p className="font-bold">Search</p>
                </div>
            </button>
        </div>

    )
}

export default SearchInput;