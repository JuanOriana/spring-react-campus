export const TRANSLATIONS_ES = {

    "DaysOfTheWeek":{
        "Lunes":"Lunes",
        "Martes":"Martes",
        "Miercoles":"Miercoles",
        "Jueves":"Jueves",
        "Viernes":"Viernes",
        "Sabado":"Sabado",
    },

    //COMPONENTS

    "AdminSectionCol": {
        "title": "Administracion del Campus",
        "Nuevo usuario": "Nuevo usuario",
        "Nuevo curso": "Nuevo curso",
        "Agregar usuario a curso": "Agregar usuario a curso",
        "Ver todos los cursos": "Ver todos los cursos",
    },

    "BasicPagination": {
        "message": "Pagina {{currentPage}} de {{maxPage}}",
        "alt": {
            "nextPage": "Siguiente pagina",
            "beforePage": "Pagina previa",
        }
    },

    "CourseSectionsCol":{
        "Anuncios":"Anuncios",
        "Profesores":"Profesores",
        "Archivos":"Archivos",
        "Examenes":"Examenes",
        "Horarios":"Horarios",
    },

    "ExamUnit":{
        "alt":{
            "exam":"Examen",
            "seeCorrections":"Ver correcciones",
            "delete":"Borrar"
        },
        "grade":"Nota: {{grade}}",
        "correctedOf":"{{examsSolved}} de {{userCount}} corregidos",
    },

    "FileSearcher":{
        "placeholder":{
          "fileName":"Escribe el nombre del archivo",
        },
        "alt":{
            "toggleFilters":"Encender filtros",
        },
        "search":"Buscar",
        "searchBy":{
            "title":"Buscar por:",
            "date":"Fecha",
            "name":"Nombre",
            "downloads":"Descargas"
        },
        "orderBy": {
            "title":"Ordenar de forma:",
            "asc":"Ascendente",
            "desc":"Descendente"
        },
        "fileType": {
            "title": "Tipo de archivo",
            "all": "Todos",
            "other": "Otra"
        },
        "category":{
            "title": "Categoria",
            "all": "Todas",
            "other": "Otra",
        },
        "clearFilters": "Limpiar Filtros",
        "filteredBy": "Filtrado por:"
    },

    "Category":{
        "practice": "Practica",
        "theory": "Teor\u00EDa",
        "exam": "Examen",
        "final": "Final",
        "guide": "Gu\u00EDa",
        "bylaws": "Reglamento",
        "schedule": "Cronograma",
    },

    "FileUnit":{
        "alt":{
            "file": "Archivo",
            "delete": "Borrar",
        }
    },

    "Footer": "Campus © {{year}} - Todos los derechos reservados",

    "Navbar":{
      "sections":{
          "/portal":"Mis cursos",
          "/announcements":"Mis anuncios",
          "/files":"Mis archivos",
          "/timetable":"Mis horarios",
      },
        "logout":"Cerrar sesion"
    },

    "StudentExamUnit":{
        "alt":{
          "check":"corregir",
        },
        "notHandedIn": "No se entrego",
    },

    ////VIEWS

    "404":{
      "title":"Error 404!",
      "backToPortalButton": "Volver al portal"
    },

    //Admin

    "AdminAddUserToCourse":{
        "alt":{
            "backButton":"Volver",
        },
        "backButton":"Volver",
        "addUserToCourse":"Anadir usuario a {{subjectName}}[{{courseBoard}}]",
        "form":{
            "user":"Usuario",
            "role":{
                "title":"Rol",
                "Student":"Alumno",
                "Teacher":"Profesor",
                "Assistant":"Ayudante",
            },
            "addButton":"Anadir usuario",
            "students":"Alumnos",
            "teachers":"Profesores",
            "assistants":"Ayudantes",
        }
    },

    "AdminAllCourses":{
        "allCoursesFrom":"Todos los cursos del {{year}}/{{quarter}}",
        "form":{
            "year":"Ano",
            "quarter":"Cuatrimestre",
            "searchButton":"Buscar",
        },
        "noCourses":"No hay cursos en este cuatrimestre",
        "table":{
            "code":"Codigo",
            "name":"Nombre",
            "board":"Comision",
        },
    },

    "AdminNewCourse":{
        "newCourse":"Nuevo curso",
        "form":{
            "subject":"Materia",
            "quarter":"Cuatrimestre",
            "year":"Ano",
            "board":"Comision",
            "createButton":"Crear"
        },
        "error":{
            "alreadyExist":"Este curso ya existe",
        },
    },

    "AdminNewUser":{
        "error":{
            "passwordsMustMatch":"La contrasenas deben concidir",
            "fileNumber":{
                "isRequired":"El legajo es requerido",
                "positiveInteger":"El legajo debe ser un numero positivo",
                "exists":"El legajo ya existe",
            },
            "name":{
                "onlyLetters":"El nombre solo puede tener letras",
                "isRequired":"El nombre es requerido",
            },
            "surname": {
                "onlyLetters":"El apellido solo puede tener letras",
                "isRequired":"El apellido es requerido",
            },
            "username": {
                "pattern":"El nombre de usuario solo puede tener letras y numeros",
                "isRequired":"El nombre de usuario es requerido",
                "length":"El largo del nombre de usuario debe estar entre 6 y 50 caracteres",
                "exists":"El nombre de usuario ya existe",
            },
            "email":{
                "pattern":"El email debe tener un formato valido",
                "isRequired":"El email es un campo requerido",
                "exists":"El email ya esta en uso",
            },
            "password":{
                "pattern":"La contrasena debe tener una mayuscula, una minsucula y un numero",
                "isRequired":"La contrasena es requerida",
                "length":"El largo de la contrasena debe estar entre 8 y 50 caracteres",
            },

        },
        "form":{
            "title":"Crear nuevo usuario",
            "fileNumber":"Legajo",
            "name":"Nombre",
            "surname":"Apellido",
            "username":"Nombre de usuario",
            "email":"Email",
            "password":"Contrasena",
            "confirmPassword":"Confirmar contrasena",
            "createButton":"Crear"
        },
    },

    "AdminPortal":{
        "title":"Centro de Administracion del Campus",
        "createNewUserButton":"Crear nuevo usuario",
        "createNewCourseButton":"Crear nuevo curso",
        "addUserToCourseButton":"Anadir usuario a curso",
        "seeAllCoursesButton":"Ver todos los cursos",
    },

    "AdminSelectCourse":{
        "title":"Seleccionar un curso",
        "form":{
            "course":"Curso",
            "selectButton":"Seleccionar",
        },
    },

    "Announcements":{
        "title":"Anuncios",
        "noAnnouncements":"No tienes anuncios!",
    },

    "CourseAnnouncements":{
        "teacher":{
            "form":{
                "title":"Nuevo Anuncio",
                "announcementTitleLabel":"Titulo",
                "announcementContentLabel":"Contenido",
                "announcementCreateButton":"Crear anuncio",
            },
            "error":{
                "title":{
                    "isRequired":"El titulo es requerido",
                    "length":"El titulo debe tener entre 2 y 50 caracteres de largo",
                },
                "content":{
                    "isRequired":"El anuncio debe tener un contenido",
                    "length":"El contenido debe tener mas de 2 caracteres de largo",
                }
            },
        },
    },

    "CorrectExam":{
        "descriptionTitle":"Descripción:",
        "solutionTitle":"Solución:",
        "examNotDone":"El examen aun no fue realizado",
        "form":{
            "title":"Corregir examen",
            "comments": "Comentarios",
            "submitButton": "Corregir",
            "cancelCorrectionButton":"Cancelar"

        },
        "error":{
            "grade":{
                "isRequired":"La nota no puede ser vacía.",
                "minGrade":"La nota minima es 0",
                "maxGrade":"La nota minima es 10",
            },
            "comments":{
                "length":"El largo de los commentarios no puede exceder los 50 caracteres",
            },
        },
    },

    "StudentCourseExamStandalone":{
        "timeLeft": "Tiempo restante: {{days}}d:{{hours}}h:{{minutes}}m:{{seconds}}s",
        "examDescriptionTitle":"Descripción:",
        "form":{
            "solutionTitle":"Solucion",
            "cancelSend":"Cancelar envio",
            "send":"Enviar",
        },
        "error":{
            "file":{
                "isRequired":"Adjuntar un archivo",
                "size":"El archivo debe ser mas chico que 50mb",
            },
        },
    },

    "TeacherCourseExamStandalone":{
        "filteredBy":"Filtrar por",
        "filters":{
            "all":"Todos",
            "corrected":"Corregidos",
            "notCorrected":"No corregidos",
        },
        "filterButton":"Filtrar",
        "noExams":"No hay examenes que cumplan el criterio"
    },

    "StudentExams":{
        "title":"Examenes",
        "toDo": "Examenes a disponibles:",
        "noExams": "No hay examenes",
        "sentExames": "Examenes enviados",
    },

    "TeacherExams":{
        "title":"Examenes",
        "createExam":"Crear examen",
        "form":{
            "examTitle":"Titulo",
            "examInstructions":"Instrucciones",
            "examFile":"Archivo",
            "examStart": "Inicio",
            "examEnd": "Fin",
            "createButton":"Crear"
        },
        "error":{
            "examTitle":{
                "isRequired":"El titulo es requerido",
                "length":"El titulo debe tener entre 2 y 50 caracteres de largo",
            },
            "examInstructions":{
                "isRequired":"El contenido es requerido",
                "length":"El contenido debe tener entre 2 y 50 caracteres de largo",
            },
            "examFile":{
                "isRequired":"El archivo es requerido",
                "size":"El archivo debe ser mas pequeño que 50mb",
            },
            "examStart":{
                "isRequired":"La fecha de inicio es requerida",
                "badDate":"La fecha de inicio debe ser previa la final",
            },
            "examEnd":{
                "isRequired":"La fecha de final es requerida",
            },
        },
        "recentExams":"Examenes recientes",
        "noExams": "No hay examenes",
    },

    "CourseFiles":{
        "teacher":{
            "form":{
                "title":"Nuevo archivo",
                "file":"Archivo",
                "category":"Categoria",
                "uploadFileButton":"Subir Archivo",
            },
            "error":{
                "file":{
                    "isRequired":"El archivo es requerido",
                    "size":"El archivo debe ser mas pequeño que 50mb",
                },
            },
        },
        "title": "Archivos",
        "noResults":"No hay resultados!",
    },

    "CourseSchedule":{
        "title":"Horarios",
        "subTitle":"Estos son tus horarios de clase, ante cualquier cambio consultar con un profesor autorizado.",
    },

    "CourseTeachers":{
        "title":"Profesores",
        "alt":{
            "mail":"Correo",
            "title":"Enviar correo"
        },
    },

    "Mail":{
        "form":{
            "title": "Enviar mail a {{name}} {{surname}}",
            "subject":"Asunto",
            "message":"Contenido",
            "sendMailButton":"Enviar"
        },
        "error":{
            "subject":{
                "isRequired":"El asunto es requerido",
            },
            "message":{
                "isRequired":"El contenido es requerido",
            },
        },
    },

    "Files":{
        "title":"Archivos",
        "noResults":"No hay resultados!",
    },

    "Login":{
        "title":"Ingresar",
        "form":{
            "user":"Nombre de usuario",
            "password":"Contraseña",
            "rememberMe":"Recuérdame",
            "loginButton":"Ingresar",
        },
        "error":{
            "user":{
                "isRequired":"Ingrese un nombre de usuario",
                "badUser": "Este usuario es inexistente",
            },
            "password":{
                "isRequired":"Ingrese una contraseña",
                "badPassword": "Contraseña incorrecta",
            },
        },
    },

    "Portal":{
        "title":"Cursos",
        "lastAnnouncements":"Últimos Anuncios"
    },

    "Timetable":{
        "title":"Mis Horarios",
    },

    "User":{
        "form":{
            "title": "Insertar imagen",
            "confirmButton":"Confirmar",
        },
        "error":{
            "file":{
                "isRequired": "La imagen es requerida",
                "size":"La imagen debe ser mas chica que 5mb",
            },
        },
        "usernameTitle":"Nombre de usuario: ",
        "emailTitle":"Email: ",
        "fileNumberTitle":"Legajo: "
    },

};