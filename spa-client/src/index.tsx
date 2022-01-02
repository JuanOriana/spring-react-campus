import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Main from "./components/layouts/Main";
import Portal from "./pages/Portal";
import Timetable from "./pages/Timetable";
import Custom404 from "./pages/404";

ReactDOM.render(
  <React.StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Main />}>
          <Route index element={<Portal />} />
          <Route path="portal" element={<Portal />} />
          <Route path="timetable" element={<Timetable />} />
          <Route path="*" element={<Custom404 />} />
        </Route>
      </Routes>
    </BrowserRouter>
  </React.StrictMode>,
  document.getElementById("root")
);
