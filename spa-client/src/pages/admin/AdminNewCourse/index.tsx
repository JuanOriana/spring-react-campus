import AdminSectionsCol from "../../../components/AdminSectionsCol";
import {
  ErrorMessage,
  FormButton,
  FormInput,
  FormLabel,
  FormSelect,
  FormWrapper,
} from "../../../components/generalStyles/form";
import { FormText } from "../AdminAllCourses/styles";
import { GeneralTitle } from "../../../components/generalStyles/utils";
import { useState } from "react";

function AdminNewCourse() {
  const subjects = [{ subjectId: 1, name: "PAW" }];
  const [isCourseDuplicated, setIsCourseDuplicated] = useState(false);

  const days: string[] = [
    "Lunes",
    "Martes",
    "Miercoles",
    "Jueves",
    "Viernes",
    "Sabado",
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
        <GeneralTitle style={{ color: "#176961", alignSelf: "center" }}>
          Nuevo curso
        </GeneralTitle>
        <FormLabel htmlFor="subjectId">Materia</FormLabel>
        <FormSelect style={{ fontSize: "26px" }}>
          {subjects.map((subject) => (
            <option value={subject.subjectId}>{subject.name}</option>
          ))}
        </FormSelect>
        <FormLabel htmlFor="quarter">Cuatrimestre</FormLabel>
        <div
          style={{
            display: "flex",
            width: "200px",
            alignItems: "center",
            justifyContent: "space-between",
            marginLeft: "20px",
            fontSize: "20px",
          }}
        >
          <div>
            <input type="radio" value="1" style={{ marginRight: "5px" }} /> 1
          </div>
          <div>
            <input type="radio" value="2" style={{ marginRight: "5px" }} /> 2
          </div>
        </div>
        <FormLabel htmlFor="year">Ano</FormLabel>
        <FormInput type="number" style={{ fontSize: "26px" }} />
        <FormLabel htmlFor="board">Comision</FormLabel>
        <FormInput type="text" style={{ fontSize: "26px" }} />
        {isCourseDuplicated && (
          <ErrorMessage>Este curso ya existe</ErrorMessage>
        )}
        <div
          style={{
            display: "grid",
            gridTemplateColumns: "400px 400px",
            margin: "20px 20px 0 20px",
          }}
        >
          {days.map((day) => (
            <div style={{ display: "flex", flexDirection: "column" }}>
              <FormText>{day}</FormText>
              <div style={{ display: "flex" }}>
                <input
                  style={{ width: "3em" }}
                  type="number"
                  min="8"
                  max="22"
                />
                <p>:00 ---- </p>
                <input
                  style={{ width: "3em" }}
                  type="number"
                  min="8"
                  max="22"
                />
                <p>:00</p>
              </div>
            </div>
          ))}
        </div>
        <FormButton>Crear</FormButton>
      </FormWrapper>
    </>
  );
}

export default AdminNewCourse;
