import AdminSectionsCol from "../../../components/AdminSectionsCol";
import {
  FormButton,
  FormLabel,
  FormSelect,
  FormWrapper,
} from "../../../components/generalStyles/form";
import { Link } from "react-router-dom";
import { GeneralTitle } from "../../../components/generalStyles/utils";
import { FormText } from "../AdminAllCourses/styles";
import { usePagination } from "../../../hooks/usePagination";
import {
  PaginationArrow,
  PaginationWrapper,
} from "../../../components/generalStyles/pagination";
import { UserColumn, UserContainer, BackImg } from "./styles";

function AdminAddUserToCourse() {
  const maxPage = 3;
  const [currentPage, pageSize] = usePagination(10);
  const course = {
    courseId: 1,
    subject: { name: "PAW" },
    board: "F",
    year: 2020,
    quarter: 2,
  };
  const users = [{ userId: 1, fileNumber: 1, name: "xd", surname: "xd" }];
  const roles = [{ roleId: 1, roleName: "xd" }];
  const courseTeachers = [
    { userId: 1, fileNumber: 1, name: "xd", surname: "xd" },
  ];
  const courseHelpers = [
    { userId: 1, fileNumber: 1, name: "xd", surname: "xd" },
  ];
  const courseStudents = [
    { userId: 1, fileNumber: 1, name: "xd", surname: "xd" },
  ];

  return (
    <>
      <AdminSectionsCol />
      <FormWrapper
        reduced={true}
        method="post"
        acceptCharset="utf-8"
        style={{ margin: "0px 40px 40px 40px" }}
      >
        <Link
          to="/admin/course/select"
          style={{ display: "flex", alignItems: "center" }}
        >
          <BackImg src="/images/page-arrow.png" alt="Volver" />
          <p style={{ fontSize: "22px", fontWeight: "700" }}>Volver</p>
        </Link>
        <GeneralTitle style={{ color: "#176961", alignSelf: "center" }}>
          Anadir usuario a {course.subject.name}[{course.board}]
        </GeneralTitle>
        <FormLabel htmlFor="userId">Usuario</FormLabel>
        <FormSelect style={{ fontSize: "26px" }}>
          {users.map((user) => (
            <option value={user.userId} key={user.userId}>
              {user.fileNumber} - {user.name} {user.surname}
            </option>
          ))}
        </FormSelect>
        <FormLabel htmlFor="roleId">Rol</FormLabel>
        <FormSelect style={{ fontSize: "26px" }}>
          {roles.map((role) => (
            <option value={role.roleId} key={role.roleId}>
              {role.roleName}
            </option>
          ))}
        </FormSelect>
        <FormButton>Anadir usuario</FormButton>
        {(courseTeachers.length > 0 ||
          courseStudents.length > 0 ||
          courseHelpers.length > 0) && (
          <UserContainer>
            <UserColumn>
              <FormText style={{ margin: 0 }}>Alumnos</FormText>
              <ul>
                {courseStudents.map((student) => (
                  <li key={student.userId}>{student.name}</li>
                ))}
              </ul>
              {maxPage > 1 && (
                <PaginationWrapper
                  style={{
                    alignSelf: "center",
                    fontSize: "14px",
                    marginRight: "25%",
                  }}
                >
                  {currentPage > 1 && (
                    <Link
                      to={`/admin/course/enroll?courseId=${
                        course.courseId
                      }&page=${currentPage - 1}&pageSize=${pageSize}`}
                    >
                      {/*ACHICAR!*/}
                      <PaginationArrow
                        xRotated={true}
                        src="/images/page-arrow.png"
                        alt="Pagina anterior"
                      />
                    </Link>
                  )}
                  Pagina {currentPage} de {maxPage}
                  {currentPage < maxPage && (
                    <Link
                      to={`/admin/course/enroll?courseId=${
                        course.courseId
                      }&page=${currentPage + 1}&pageSize=${pageSize}`}
                    >
                      <PaginationArrow
                        src="/images/page-arrow.png"
                        alt="Pagina siguiente"
                      />
                    </Link>
                  )}
                </PaginationWrapper>
              )}
            </UserColumn>
            <UserColumn>
              {courseTeachers.length > 0 && (
                <>
                  <FormText style={{ margin: 0 }}>Profesores</FormText>
                  <ul>
                    {courseTeachers.map((teacher) => (
                      <li key={teacher.userId}>{teacher.name}</li>
                    ))}
                  </ul>
                </>
              )}
              {courseHelpers.length > 0 && (
                <>
                  <FormText style={{ margin: 0 }}>Ayudantes</FormText>
                  <ul>
                    {courseHelpers.map((helper) => (
                      <li key={helper.userId}>{helper.name}</li>
                    ))}
                  </ul>
                </>
              )}
            </UserColumn>
          </UserContainer>
        )}
      </FormWrapper>
    </>
  );
}

export default AdminAddUserToCourse;
