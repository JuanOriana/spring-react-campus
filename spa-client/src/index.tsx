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
import CourseFiles from "./pages/courses/CourseFiles";
import Mail from "./pages/courses/Mail";
import { ThemeProvider } from "styled-components";

const theme = {
  cyanDarkest: "#176961",
  cyanDark: "#2EC4B6",
  cyanLight: "#CBF3F0",
  successBack: "#45FF9F",
  successFront: "#39614C",
};

ReactDOM.render(
  <React.StrictMode>
    <ThemeProvider theme={theme}>
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
            <Route path="course/:courseId/mail/:mailId" element={<Mail />} />
            <Route path="course/:courseId" element={<CourseLayout />}>
              <Route index element={<Navigate to={"announcements"} />} />
              <Route path={"announcements"} element={<CourseAnnouncements />} />
              <Route path={"files"} element={<CourseFiles />} />
              <Route path={"teachers"} element={<CourseTeachers />} />
              <Route path={"schedule"} element={<CourseSchedule />} />
            </Route>
          </Route>
        </Routes>
      </BrowserRouter>
    </ThemeProvider>
  </React.StrictMode>,
  document.getElementById("root")
);

// Servicios y modelos
// Type definition typescript
// endpoint -> servicio + modelos
