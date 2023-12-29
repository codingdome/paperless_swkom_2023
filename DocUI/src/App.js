import React, { useEffect, useState, Suspense, lazy } from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import LazyComponent from "./optimization/LazyComponent";

const Navbar = lazy(() => import("./components/Navbar"));
const Footer = lazy(() => import("./components/Footer"));

const Impressum = lazy(() => import("./components/pages/Impressum"));
const Datenschutz = lazy(() => import("./components/pages/Datenschutz"));

// import Navbar from "./components/Navbar";
// import Footer from "./components/Footer";

// import Impressum from "./components/Impressum";
// import Datenschutz from "./components/Datenschutz";

// import { initObserver } from "./scrollObserver";

import { consoleAsciiArt } from "./utility/consoleLogger"

import ArrowUp from "../src/assets/img/icons/arrow-up.svg";
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import DocumentBrowser from "./components/pages/DocumentBrowser";

const Dashboard = lazy(() => import("./components/pages/Dashboard"));

function App() {
    const scrollToTop = () => {
        window.scrollTo({ top: 0, behavior: 'smooth' });
    }

    useEffect(() => {
        // initObserver();
    })

    return (
        <div className="text-[#F3F3F4]">



            <BrowserRouter>
                <Suspense fallback={<div>Loading...</div>}>
                    <Navbar />
                </Suspense>

                <Routes>
                    <Route path="/" element={
                        <>


                            <DocumentBrowser />



                            {/* GoUp Button */}
                            <button
                                onClick={scrollToTop}
                                className="fixed bottom-5 right-5 px-3 py-3 rounded-full bg-[#0020FF] hover:bg-[#15161D] transition-all"
                                aria-label="scroll to top"
                            >
                                <img className="w-[30px] h-[30px]" src={ArrowUp} alt="Arrow up" />
                            </button>
                        </>
                    } />

                    <Route path="/dashboard" element={
                        <Suspense fallback={<div>Loading...</div>}>
                            <Dashboard />
                        </Suspense>
                    } />

                    <Route path="/impressum" element={
                        <Suspense fallback={<div>Loading...</div>}>
                            <Impressum />
                        </Suspense>
                    } />

                    <Route path="/datenschutz" element={
                        <Suspense fallback={<div>Loading...</div>}>
                            <Datenschutz />
                        </Suspense>
                    } />
                </Routes>

                <Suspense fallback={<div>Loading...</div>}>
                    <Footer />
                </Suspense>

            </BrowserRouter>



            <div className="z-[16000]">
                <ToastContainer style={{ zIndex: 30000 }} />
            </div>
        </div>
    );
}

export default App;