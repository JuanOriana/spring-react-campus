export const TRANSLATIONS_ES = {
  DaysOfTheWeek: {
    Lunes: "Lunes",
    Martes: "Martes",
    Miercoles: "Miércoles",
    Jueves: "Jueves",
    Viernes: "Viernes",
    Sabado: "Sábado",
  },

  //COMPONENTS

  AdminSectionCol: {
    title: "Administración del Campus",
    "Nuevo usuario": "Nuevo usuario",
    "Nuevo curso": "Nuevo curso",
    "Agregar usuario a curso": "Agregar usuario a curso",
    "Ver todos los cursos": "Ver todos los cursos",
  },

  AnnouncementUnit: {
    author: "Autor: {{name}} {{surname}}",
    subject: "Materia: {{subjectName}}",
    alt: {
      deleteButton: "Borrar"
    },
    readMoreButton: "...leer más",
    readLessButton: "ocultar",
  },

  BasicPagination: {
    message: "Página {{currentPage}} de {{maxPage}}",
    alt: {
      nextPage: "Siguiente página",
      beforePage: "Página previa",
    },
  },

  CourseSectionsCol: {
    Anuncios: "Anuncios",
    Profesores: "Profesores",
    Archivos: "Archivos",
    Examenes: "Exámenes",
    Horarios: "Horarios",
  },

  ExamUnit: {
    alt: {
      exam: "Examen",
      seeCorrections: "Ver correcciones",
      delete: "Borrar",
    },
    grade: "Nota: {{grade}}",
    correctedOf: "{{examsSolved}} de {{userCount}} corregidos",
  },

  FileSearcher: {
    placeholder: {
      fileName: "Escribe el nombre del archivo",
    },
    alt: {
      toggleFilters: "Encender filtros",
    },
    search: "Buscar",
    searchBy: {
      title: "Buscar por:",
      date: "Fecha",
      name: "Nombre",
      downloads: "Descargas",
    },
    orderBy: {
      title: "Ordenar de forma:",
      asc: "Ascendente",
      desc: "Descendente",
    },
    fileType: {
      title: "Tipo de archivo",
      all: "Todos",
      other: "Otra",
    },
    category: {
      title: "Categoría",
      all: "Todas",
      other: "Otra",
    },
    clearFilters: "Limpiar Filtros",
    filteredBy: "Filtrado por:",
  },

  Category: {
    practice: "Práctica",
    theory: "Teor\u00EDa",
    exam: "Examen",
    final: "Final",
    guide: "Gu\u00EDa",
    bylaws: "Reglamento",
    schedule: "Cronograma",
    other: "Otra",
  },

  FileUnit: {
    alt: {
      file: "Archivo",
      delete: "Borrar",
    },
    downloads: "Descargas: {{downloads}}",
  },

  Footer: "Campus © {{year}} - Todos los derechos reservados",

  Navbar: {
    sections: {
      "/portal": "Mis cursos",
      "/announcements": "Mis anuncios",
      "/files": "Mis archivos",
      "/timetable": "Mis horarios",
    },
    logout: "Cerrar sesión",
  },

  StudentExamUnit: {
    alt: {
      check: "corregir",
    },
    notHandedIn: "No se entregó",
  },

  ////VIEWS

  404: {
    title: "¡Error 404!",
    backToPortalButton: "Volver al portal",
  },

  //Admin

  AdminAddUserToCourse: {
    alt: {
      backButton: "Volver",
    },
    backButton: "Volver",
    addUserToCourse: "Añadir usuario a {{subjectName}}[{{courseBoard}}]",
    form: {
      user: "Usuario",
      role: {
        title: "Rol",
        Student: "Alumno",
        Teacher: "Profesor",
        Assistant: "Ayudante",
      },
      addButton: "Añadir usuario",
      students: "Alumnos",
      teachers: "Profesores",
      assistants: "Ayudantes",
    },
    toast: {
      error:{
        userNotAdded: "No se pudo agregar el usuario al curso, por favor intente de nuevo",
      },
    },
  },

  AdminAllCourses: {
    allCoursesFrom: "Cursos del {{year}}/{{quarter}}Q",
    form: {
      year: "Año",
      quarter: "Cuatrimestre",
      searchButton: "Buscar",
    },
    noCourses: "No hay cursos en este cuatrimestre",
    table: {
      code: "Código",
      name: "Nombre",
      board: "Comisión",
    },
  },

  AdminNewCourse: {
    newCourse: "Nuevo curso",
    form: {
      subject: "Materia",
      quarter: "Cuatrimestre",
      year: "Año",
      board: "Comisión",
      createButton: "Crear",
    },
    error: {
      alreadyExist: "Este curso ya existe",
    },
    toast:{
      error:{
        notCreated: "No se pudo crear el curso, por favor intente de nuevo"
      },
      message:{
        createdCorrectly: "👑 ¡Curso creado exitosamente!",
      },
    },
  },

  AdminNewUser: {
    error: {
      passwordsMustMatch: "Las contraseñas deben coincidir",
      fileNumber: {
        isRequired: "El legajo es requerido",
        positiveInteger: "El legajo debe ser un número positivo",
        exists: "Legajo en uso",
      },
      name: {
        onlyLetters: "El nombre solo puede contener letras",
        isRequired: "El nombre es requerido",
      },
      surname: {
        onlyLetters: "El apellido solo puede contener letras",
        isRequired: "El apellido es requerido",
      },
      username: {
        pattern: "El nombre de usuario solo puede contener letras y números",
        isRequired: "El nombre de usuario es requerido",
        length:
            "El largo del nombre de usuario debe estar entre 6 y 50 caracteres",
        exists: "El nombre de usuario ya esta en uso",
      },
      email: {
        pattern: "El email debe tener un formato valido",
        isRequired: "El email es un campo requerido",
        exists: "El email ya está en uso",
      },
      password: {
        pattern:
            "La contraseña debe tener una mayúscula, una minúscula y un número",
        isRequired: "La contraseña es requerida",
        length: "El largo de la contraseña debe estar entre 8 y 50 caracteres",
      },
      repeated: "El nombre de usuario, email y legajo deben ser únicos",
    },
    form: {
      title: "Crear nuevo usuario",
      fileNumber: "Legajo",
      name: "Nombre",
      surname: "Apellido",
      username: "Nombre de usuario",
      email: "Email",
      password: "Contraseña",
      confirmPassword: "Confirmar contraseña",
      createButton: "Crear",
    },
    toast:{
      error:{
        notCreated: "No se pudo crear el usuario, por favor intente de nuevo"
      },
      message:{
        createdCorrectly: "👑 ¡Usuario creado exitosamente!",
      },
    },
  },

  AdminPortal: {
    title: "Centro de Administración del Campus",
    createNewUserButton: "Crear nuevo usuario",
    createNewCourseButton: "Crear nuevo curso",
    addUserToCourseButton: "Añadir usuario a curso",
    seeAllCoursesButton: "Ver todos los cursos",
  },

  AdminSelectCourse: {
    title: "Seleccionar un curso",
    form: {
      course: "Curso",
      selectButton: "Seleccionar",
    },
  },

  Announcements: {
    title: "Anuncios",
    noAnnouncements: "¡Aún no tienes anuncios!",
  },

  CourseAnnouncements: {
    teacher: {
      form: {
        title: "Nuevo Anuncio",
        announcementTitleLabel: "Título",
        announcementContentLabel: "Contenido",
        announcementCreateButton: "Crear anuncio",
      },
      error: {
        title: {
          isRequired: "El título es requerido",
          length: "El título debe tener entre 2 y 50 caracteres de largo",
        },
        content: {
          isRequired: "El anuncio debe tener un contenido",
          length: "El contenido debe tener más de 2 caracteres de largo",
        },
      },
      alert:{
        deleteAnnouncement: "¿Estas seguro que desea eliminar este anuncio?",
      },
      toast:{
        error:{
          notRemoved: "No se pudo borrar el anuncio, por favor intente de nuevo",
          notCreated: "No se pudo crear el anuncio, por favor intente de nuevo"
        },
        message:{
          removedCorrectly: "👑 ¡Anuncio eliminado exitosamente!",
          createdCorrectly: "👑 ¡Anuncio creado exitosamente!",
        },
      },
    },
  },

  CorrectExam: {
    descriptionTitle: "Descripción:",
    solutionTitle: "Solución:",
    examNotDone: "El examen no fue realizado aún",
    form: {
      title: "Corregir examen",
      comments: "Comentarios",
      submitButton: "Corregir",
      cancelCorrectionButton: "Cancelar",
    },
    error: {
      grade: {
        isRequired: "La nota no puede ser vacía.",
        minGrade: "La nota mínima es 0",
        maxGrade: "La nota mínima es 10",
      },
      comments: {
        length:
            "El largo de los comentarios no puede exceder los 50 caracteres",
      },
    },
    toast:{
      error:{
        notCorrected: "No se pudo corregir el examen, por favor intente de nuevo",
      },
      message:{
        correctedCorrectly: "👑 Se corrigió el examen con {{mark}}",
      },
    },
  },

  StudentCourseExamStandalone: {
    timeLeft: "Tiempo restante: {{days}}d:{{hours}}h:{{minutes}}m:{{seconds}}s",
    examDescriptionTitle: "Descripción:",
    form: {
      solutionTitle: "Solución",
      cancelSend: "Cancelar envío",
      send: "Enviar",
    },
    error: {
      file: {
        isRequired: "Adjuntar un archivo",
        size: "El archivo debe ser más chico que 50mb",
      },
    },
    toast:{
      error:{
        notSent: "No se pudo enviar la respuesta, por favor intente de nuevo",
      },
      message:{
        sentCorrectly: "👑 ¡Examen enviado exitosamente!",
      },
    },
  },

  TeacherCourseExamStandalone: {
    filteredBy: "Filtrar por",
    filters: {
      all: "Todos",
      corrected: "Corregidos",
      notCorrected: "No corregidos",
    },
    filterButton: "Filtrar",
    noExams: "No hay exámenes bajo esa categoría",
    toast:{
      error:{
        notUndoCorrection: "No se pudo anular la corrección del examen, por favor intente de nuevo",
      },
      message:{
        undoCorrectly: "👑 Se eliminó la corrección del examen",
      },
    },
    alert: {
        undoCorrection: "¿Estas seguro que desea eliminar esta corrección?"
    },
  },

  StudentExams: {
    title: "Exámenes",
    toDo: "Exámenes a disponibles:",
    noExams: "No hay exámenes",
    sentExams: "Exámenes enviados",
  },

  TeacherExams: {
    title: "Exámenes",
    createExam: "Crear examen",
    form: {
      examTitle: "Título",
      examInstructions: "Instrucciones",
      examFile: "Archivo",
      examStart: "Inicio",
      examEnd: "Fin",
      createButton: "Crear",
    },
    error: {
      examTitle: {
        isRequired: "El título es requerido",
        length: "El título debe tener entre 2 y 50 caracteres de largo",
      },
      examInstructions: {
        isRequired: "El contenido es requerido",
        length: "El contenido debe tener entre 2 y 50 caracteres de largo",
      },
      examFile: {
        isRequired: "El archivo es requerido",
        size: "El archivo debe ser más pequeño que 50mb",
      },
      examStart: {
        isRequired: "La fecha de inicio es requerida",
        badDate: "La fecha de inicio debe ser previa la final",
      },
      examEnd: {
        isRequired: "La fecha de final es requerida",
      },
    },
    recentExams: "Exámenes recientes",
    noExams: "No hay exámenes",
    toast:{
      error:{
        notCreated: "No se pudo crear el examen, por favor intente de nuevo",
        notRemoved: "No se pudo eliminar el examen, por favor intente de nuevo",
      },
      message:{
        createdCorrectly: "👑 ¡Examen creado exitosamente!",
        removedCorrectly: "👑 ¡Examen eliminado exitosamente!",
      },
    },
    alert: {
      deleteExam: "¿Seguro que desea eliminar este examen?",
    },
  },

  CourseFiles: {
    teacher: {
      form: {
        title: "Nuevo archivo",
        file: "Archivo",
        category: "Categoría",
        uploadFileButton: "Subir Archivo",
      },
      error: {
        file: {
          isRequired: "El archivo es requerido",
          size: "El archivo debe ser más pequeño que 50mb",
        },
      },
    },
    title: "Archivos",
    noResults: "¡No hay resultados!",
    toast:{
      error:{
        notUploaded: "No se pudo subir el archivo, por favor intente de nuevo",
        notRemoved: "No se pudo eliminar el archivo, por favor intente de nuevo",
      },
      message:{
        uploadedCorrectly: "👑 ¡Archivo subido exitosamente!",
        removedCorrectly: "👑 ¡Archivo eliminado exitosamente!",
      },
    },
    alert: {
      deleteFile: "¿Seguro que desea eliminar este archivo?",
    },
  },

  CourseSchedule: {
    title: "Horarios",
    subTitle:
        "Los horarios de clase son:",
  },

  CourseTeachers: {
    title: "Profesores",
    alt: {
      mail: "Correo",
      title: "Enviar correo",
    },
  },

  Mail: {
    form: {
      title: "Enviar email a {{name}} {{surname}}",
      subject: "Asunto",
      message: "Contenido",
      sendMailButton: "Enviar",
    },
    error: {
      subject: {
        isRequired: "El asunto es requerido",
      },
      message: {
        isRequired: "El contenido es requerido",
      },
    },
    toast:{
      error:{
        notSent: "No se pudo enviar el correo, por favor intente de nuevo",
      },
      message:{
        sentCorrectly: "👑 ¡Correo enviado exitosamente!",
      },
    },
  },

  Files: {
    title: "Archivos",
    noResults: "¡No hay resultados para la búsqueda!",
  },

  Login: {
    title: "Ingresar",
    form: {
      user: "Nombre de usuario",
      password: "Contraseña",
      rememberMe: "Recuérdame",
      loginButton: "Ingresar",
    },
    error: {
      user: {
        isRequired: "Ingrese un nombre de usuario",
        badUser: "Este usuario es inexistente",
      },
      password: {
        isRequired: "Ingrese una contraseña",
        badPassword: "Contraseña incorrecta",
      },
      invalidCredentials: "Credenciales inválidas",
    },
  },

  Portal: {
    title: "Cursos",
    lastAnnouncements: "Últimos Anuncios",
    noAnnouncements: "¡No hay anuncios recientes!",
  },

  Timetable: {
    title: "Mis Horarios",
  },

  User: {
    form: {
      title: "Insertar imagen",
      confirmButton: "Confirmar",
    },
    error: {
      file: {
        isRequired: "La imagen es requerida",
        size: "La imagen debe ser menor a 5mb",
      },
    },
    usernameTitle: "Nombre de usuario: ",
    emailTitle: "Email: ",
    fileNumberTitle: "Legajo: ",
    toast:{
      error:{
        notChanged: "No se pudo actualizar la imagen, por favor intente de nuevo",
      },
      message:{
        changedCorrectly: "👑 ¡Imagen actualizada exitosamente!",
      },
    },
  },
};
