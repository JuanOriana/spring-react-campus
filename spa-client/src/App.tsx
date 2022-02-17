import { ThemeProvider } from "styled-components";
import { AuthProvider } from "./contexts/AuthContext";
import React from "react";
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import MainLayout from "./components/layouts/MainLayout";
import CourseLayout from "./components/layouts/CourseLayout";
import RequireAuth from "./components/RequireAuth";
import AdminLayout from "./components/layouts/AdminLayout";
import "react-toastify/dist/ReactToastify.css";

import {
  Portal,
  Timetable,
  Announcements,
  Files,
  User,
  Custom404,
  CourseAnnouncements,
  CourseExams,
  CourseFiles,
  Mail,
  CourseSchedule,
  CourseExamStandalone,
  CorrectExam,
  CourseTeachers,
  AdminAllCourses,
  AdminNewCourse,
  AdminNewUser,
  AdminSelectCourse,
  AdminPortal,
  AdminAddUserToCourse,
  Login,
  Error,
} from "./pages";
import { ToastContainer } from "react-toastify";

const theme = {
  cyanDarkest: "#176961",
  cyanDark: "#2EC4B6",
  cyanLight: "#CBF3F0",
  successBack: "#45FF9F",
  successFront: "#39614C",
};

function App() {
  return (
    <ThemeProvider theme={theme}>
      <AuthProvider>
        <BrowserRouter basename={process.env.REACT_APP_CONTEXT}>
          <Routes>
            <Route
              path="/"
              element={
                <RequireAuth>
                  <MainLayout />
                </RequireAuth>
              }
            >
              <Route index element={<Navigate to={"/portal"} />} />
              <Route path="portal" element={<Portal />} />
              <Route path="timetable" element={<Timetable />} />
              <Route path="announcements" element={<Announcements />} />
              <Route path="files" element={<Files />} />
              <Route path="user" element={<User />} />
              <Route path="error" element={<Error />} />
              <Route path="*" element={<Custom404 />} />
              <Route path="course/:courseId/mail/:userId" element={<Mail />} />
              <Route path="course/:courseId" element={<CourseLayout />}>
                <Route index element={<CourseAnnouncements />} />
                <Route path="announcements" element={<CourseAnnouncements />} />
                <Route path="files" element={<CourseFiles />} />
                <Route path="exams" element={<CourseExams />} />
                <Route path="exam/:examId" element={<CourseExamStandalone />} />
                <Route
                  path="exam/:examId/answer/:answerId/correct"
                  element={<CorrectExam />}
                />
                <Route path="teachers" element={<CourseTeachers />} />
                <Route path="schedule" element={<CourseSchedule />} />
              </Route>
            </Route>
            <Route
              path="admin"
              element={
                <RequireAuth>
                  <AdminLayout />
                </RequireAuth>
              }
            >
              <Route index element={<AdminPortal />} />
              <Route path="user/new" element={<AdminNewUser />} />
              <Route path="course/all" element={<AdminAllCourses />} />
              <Route path="course/select" element={<AdminSelectCourse />} />
              <Route path="course/new" element={<AdminNewCourse />} />
              <Route
                path="course/:courseId/enroll"
                element={<AdminAddUserToCourse />}
              />
            </Route>
            <Route path="/login" element={<Login />} />
          </Routes>
        </BrowserRouter>
        <ToastContainer
          position="top-left"
          autoClose={5000}
          hideProgressBar={false}
          newestOnTop={false}
          closeOnClick
          rtl={false}
          pauseOnFocusLoss
          draggable
          pauseOnHover
        />
      </AuthProvider>
    </ThemeProvider>
  );
}

export default App;
