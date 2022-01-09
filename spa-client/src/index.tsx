import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import MainLayout from "./components/layouts/MainLayout";
import CourseLayout from "./components/layouts/CourseLayout";
import Portal from "./pages/Portal";
import Timetable from "./pages/Timetable";
import Custom404 from "./pages/404";
import Announcements from "./pages/Announcements";
import User from "./pages/User";
import Files from "./pages/Files";
import CourseSchedule from "./pages/courses/CourseSchedule";
import CourseTeachers from "./pages/courses/CourseTeachers";
import CourseAnnouncements from "./pages/courses/CourseAnnouncements";

ReactDOM.render(
  <React.StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<MainLayout />}>
          <Route index element={<Navigate to={"/portal"} />} />
          <Route path="portal" element={<Portal />} />
          <Route path="timetable" element={<Timetable />} />
          <Route path="announcements" element={<Announcements />} />
          <Route path="files" element={<Files />} />
          <Route path="user" element={<User />} />
          <Route path="*" element={<Custom404 />} />
          <Route path="course/:courseId" element={<CourseLayout />}>
            <Route index element={<Navigate to={"announcements"} />} />
            <Route path={"announcements"} element={<CourseAnnouncements />} />
            <Route path={"teachers"} element={<CourseTeachers />} />
            <Route path={"schedule"} element={<CourseSchedule />} />
          </Route>
        </Route>
      </Routes>
    </BrowserRouter>
  </React.StrictMode>,
  document.getElementById("root")
);

// Servicios y modelos
// Type definition typescript
// endpoint -> servicio + modelos
