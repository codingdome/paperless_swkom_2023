import React from "react";
import Container from "../features/container/Container";
import SQLStatus from "../features/dashboard/SQLStatus";
import MinioStatus from "../features/dashboard/MinioStatus";
import ElasticSearchStatus from "../features/dashboard/ElasticSearchStatus";
import PortMapping from "../features/dashboard/PortMapping";
import ServerStatus from "../features/dashboard/ServerStatus";
import IntegrationTest from "../features/dashboard/IntegrationTest";

const Dashboard = () => {

    return (
        <div className="w-full h-full text-[#F3F3F4]">

            <Container>

                <h1 className="text-3xl font-bold mb-8">Dashboard</h1>

                <div className="w-full flex flex-col gap-2 mt-8">

                    <p className="font-bold">Integration Test</p>

                    <IntegrationTest />

                </div>

                <div className="w-full grid grid-cols-1 md:grid-cols-3 gap-x-4 gap-y-2 my-8">
                    <p className="col-span-1 md:col-span-3 font-bold">Server Status</p>

                    <ServerStatus url={"http://localhost:8081/health"} name={"Rest Server"} info={"This Rest server (DocREST) running on port 8081 is the main entry point for Doc. It handles file CRUD operations as well as search operations. Moreover it sends new files into RabbitMQ queue to be handled over to the OCR Server handling the OCR Worker."} />

                    <ServerStatus url={"http://localhost:8082/health"} name={"OCR Server"} info={"The OCR Server (DocSERVICES) handles as the name suggests the OCR Work by using PDFBox or Tesseract (depending on file input) getting the file from MinIO and saving the results to the PostgreSQL Database and ElasticSearch."} />

                    <ServerStatus url={"http://localhost:8083/health"} name={"Protocol Server"} info={"The Protocol Server (DocPROTOCOL) handles the data status monitoring as well as some integration tests for the other two servers. It could / should be the main entry point for all monitoring activites and could be enhanced over time."} />

                </div>

                <div className="w-full grid grid-cols-1 md:grid-cols-3 lg:grid-cols-3 gap-x-4 gap-y-2 my-8">

                    <p className="col-span-1 md:col-span-3 lg:col-span-3 font-bold">Data Status</p>

                    <SQLStatus />

                    <MinioStatus />

                    <ElasticSearchStatus />

                </div>

                <PortMapping />

            </Container>

        </div>

    )
}

export default Dashboard;