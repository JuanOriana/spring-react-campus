export const TRANSLATIONS_ES = {

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
        "days":{
            "Lunes":"Lunes",
            "Martes":"Martes",
            "Miercoles":"Miercoles",
            "Jueves":"Jueves",
            "Viernes":"Viernes",
            "Sabado":"Sabado",
        },
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


    ////////////////////////////////////////////////////// ERROR MESSAGES //////////////////////////////////////////////////////

    ////////AnnouncementForm error messages

    Size_announcementForm_title: "El t\u00EDtulo debe contener al menos {{2}} caracteres y como m\u00E1ximo {{1}}.",
    Size_announcementForm_content: "El contenido debe contener al menos {{2}} caracteres.",

    ////////MailForm error messages

    NotBlank_mailForm_subject: " El asunto del correo electr\u00F3nico no puede ser vac\u00EDo.",
    NotBlank_mailForm_content: " El contenido del correo electr\u00F3nico no puede ser vac\u00EDo.",

    ////////FileForm error messages

    NotNull_fileForm_file: " Se debe adjuntar un archivo.",
    MaxFileSize_fileForm_file: " El tama\u00F1o m\u00E1ximo de un archivo es de {{1}}MB.",
    NotNull_fileForm_categoryId: " Se le debe establecer una categor\u00EDa al archivo.",

    ////////EmailForm error messages

    Email_emailForm: " Ingrese un correo electr\u00F3nico valido.",

    ////////CreateExamForm error messages

    Size_createExamForm_title: "El t\u00EDtulo debe contener al menos {{2}} caracteres y como m\u00E1ximo {{1}}.",
    Size_createExamForm_content: "La consigna debe contener al menos {{2}} caracteres y como m\u00E1ximo {{1}}.",
    NotEmptyFile_createExamForm_file: "Se debe incluir un archivo.",
    MaxFileSize_createExamForm_file: "El tama\u00F1o m\u00E1ximo de un archivo es de {{1}}MB.",
    NotNull_createExamForm_startTime: "El examen debe contener una fecha de inicio.",
    NotBlank_createExamForm_startTime: "La fecha de inicio no puede ser vac\u00EDa.",
    NotNull_createExamForm_endTime: "La fecha de fin debe estar despues de la fecha de inicio.",
    NotBlank_createExamForm_endTime: "La fecha de fin no puede ser vac\u00EDa.",

    ////////SolveExamForm error messages

    NotEmptyFile_solveExamForm_exam: "Se debe incluir un archivo.",
    MaxFileSize_solveExamForm_exam: "El tama\u00F1o m\u00E1ximo de un archivo es de {{1}}MB.",

    ////////AnswerCorrectionForm error messages
    Size_answerCorrectionForm_comments: "Los comentarios pueden tener un largo de entre {{2}} y {{1}} caracteres.",
    NotNull_answerCorrectionForm_mark: "La nota no puede ser vac\u00EDa.",
    Max_answerCorrectionForm_mark: "La nota maxima es un {{1}}.",
    Min_answerCorrectionForm_mark: "La nota minima es un {{1}}.",

    ////////UserProfileForm
    NotEmptyFile_userProfileForm_image: "Se debe incluir una imagen.",
    MaxFileSize_userProfileForm_image: "El tama\u00F1o m\u00E1ximo de la imagen es de {{1}}MB.",

    ////////////////////////// ADMIN ERRORS //////////////////////////

    ////////UserRegisterForm error messages

    Min_userRegisterForm_fileNumber: "El legajo debe ser mayor a {{1}}.",
    Max_userRegisterForm_fileNumber: "El legajo debe ser menor a {{1}}.",
    typeMismatch_userRegisterForm_fileNumber: "No puede estar vac\u00EDo.",
    NotNull_userRegisterForm_fileNumber: "No puede estar vac\u00EDo.",
    Pattern_userRegisterForm_name: "El nombre debe contener solo letras.",
    NotBlank_userRegisterForm_name: "No puede estar vac\u00EDo.",
    Pattern_userRegisterForm_surname: "El apellido debe contener solo letras.",
    NotBlank_userRegisterForm_surname: "No puede estar vac\u00EDo.",
    Size_userRegisterForm_username: "El nombre de usuario debe tener entre {{2}} y {{1}} caracteres.",
    Pattern_userRegisterForm_username: "El nombre de usuario puede contener solo letras y numeros.",
    NotBlank_userRegisterForm_username: "No puede estar vac\u00EDo.",
    Email_userRegisterForm_email: "Ingrese un correo electr\u00F3nico valido.",
    Size_userRegisterForm_email: "No puede estar vac\u00EDo.",
    NotBlank_userRegisterForm_email: "No puede estar vac\u00EDo.",
    Pattern_userRegisterForm_password: "La contrase\u00F1a debe contener al menos: una letra min\u00FAscula, una letra may\u00FAscula y un n\u00FAmero.",
    Size_userRegisterForm_password: "La contrase\u00F1a debe tener entre {{2}} y {{1}} caracteres.",
    NotBlank_userRegisterForm_password: "No puede estar vac\u00EDo.",
    NotBlank_userRegisterForm_confirmPassword: "No puede estar vac\u00EDo y debe coincidir con la contrase\u00F1a introducida.",
    NotNull_userRegisterForm_confirmPassword: "Las contrase\u00F1as no coinciden.",

    ////////CourseForm error messages
    Min_courseForm_quarter: "El cuatrimestre debe ser mayor a {{1}}.",
    Max_courseForm_quarter: "El cuatrimestre debe ser menor a {{1}}.",
    NotNull_courseForm_quarter: "No puede estar vac\u00EDo.",
    NotNull_courseForm_board: "No puede estar vac\u00EDo.",
    NotBlank_courseForm_board: "No puede estar vac\u00EDo.",
    Length_courseForm_board: "La comisi\u00F3n debe tener un largo m\u00E1ximo de {{1}} caracteres.",
    Min_courseForm_year: "El a\u00F1o debe ser mayor a {{1}}.",
    NotNull_courseForm_year: "No puede estar vac\u00EDo.",

    ////////FileForm error messages
    NotEmptyFile_fileForm_file: "Se debe adjuntar un archivo.",


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////// FRONT-END STRINGS ////////////////////////////////////////////////////

    ////////403_jsp
    _403_page_title: "Campus - Error",
    _403_page_message: "403 : Acceso denegado",

    ////////404_jsp
    _404_page_title: "Campus - Error",
    _404_page_message: "404 : No encontramos lo que estas buscando",

    ////////400_jsp
    _400_page_title: "Campus - Error",
    _400_page_message: "400 : El recurso no existe",

    ////////500_jsp
    _500_page_title: "Campus - Error",
    _500_page_message: "500 : Error interno del servidor",

    ////////announcements_jsp
    announcements_page_title: "Campus - Anuncios",
    announcements_section_heading_title: "Mis Anuncios",
    announcement_title: "{{0}}",
    announcement_no_announcement: "No hay anuncios a\u00FAn",
    page_actual: "P\u00E1gina {{0}} de {{1}}",

    ////////course_jsp
    course_section_heading_title: "Anuncios",

    ////////course-files_jsp
    course_file_section_heading_title: "Material",

    ////////error-page_jsp
    errorPage_page_title: "Campus - Error",
    errorPage_message: "{{0}}",

    ////////files_jsp
    files_page_title: "Campus - Material",
    files_section_heading_title: "Mi Material",

    ////////login_jsp
    login_page_title: "Campus - Login",
    login_section_heading_title: "Ingresar",
    login_label_user: "Nombre de usuario",
    login_label_password: "Contrase\u00F1a",
    login_label_remember_me: "Recu\u00E9rdame",
    login_bad_credentials: "Las credenciales no son validas",
    login_button_login: "Ingresar",

    ////////portal_jsp
    portal_section_heading_title: "Mis Cursos",
    portal_subject_board_name: "{{0}} - Comisi\u00F3n: {{1}}",
    portal_course_info: "{{0}}/{{1}}Q",
    portal_last_announcements: "\u00DAltimos Anuncios",

    ////////sendmail_jsp
    sendmail_title_to: "Enviar un mail a {{0}} {{1}}",
    sendmail_label_subject: "Asunto",
    sendmail_label_content: "Contenido",
    sendmail_button_send: "Enviar",

    ////////teachers_jsp
    teachers_section_heading_title: "Profesores",
    teachers_teacher_name: "{{0}} {{1}}",
    teachers_teacher_email: "{{0}}",
    teachers_mail_icon_hover_text: "Enviar un email",

    ////////course-schedule_jsp
    course_schedule_section_heading_title: "Horarios",
    course_schedule_comment: "Estos son tus horarios de clase, ante cualquier cambio consultar con un profesor autorizado.",

    ////////course_exams_jsp
    course_exams_section_heading_title: "Ex\u00E1menes",
    course_exams_comment: "Ex\u00E1menes pendientes:",
    course_exams_no_exams: "No hay ex\u00E1menes por resolver",
    course_exams_sent_exams: "Ex\u00E1menes enviados",
    course_exams_sent_average: "Promedio: {{0}}",

    ////////timetable_jsp
    timetable_page_title: "Campus - Horarios",
    timetable_section_heading_title: "Mis Horarios",
    day_Monday: "Lunes",
    day_Tuesday: "Martes",
    day_Wednesday: "Mi\u00E9rcoles",
    day_Thursday: "Jueves",
    day_Friday: "Viernes",
    day_Saturday: "S\u00E1bado",

    ////////user_jsp
    user_name: "{{0}} {{1}}",
    user_username_title: "Nombre de usuario:",
    user_username: "{{0}}",
    user_email_title: "Email:",
    user_email: "{{0}}",
    user_filenumber_title: "Legajo:",
    user_filenumber: "{{0}}",
    user_insert_image_title: "Insertar imagen",
    user_insert_image_button: "Confirmar",

    ////////correct-exam_jsp
    teacher_correct_exam_none_to_correct: "No hay ex\u00E1menes por corregir",
    teacher_correct_exam_none: "No hay resultados",
    teacher_correct_exam_filter_by: "Filtrar por:",
    teacher_correct_exam_filter_all: "Todos",
    teacher_correct_exam_filter_corrected: "Corregidos",
    teacher_correct_exam_filter_not_corrected: "No corregidos",
    teacher_correct_exam_filter: "Filtrar",
    teacher_correct_exam_average: "Promedio: {{0}}",

    ////////teacher-course_jsp
    teacher_course_section_heading: "Anuncios",
    teacher_course_new_announcement: "Crear nuevo anuncio",
    teacher_course_new_announcement_title: "T\u00EDtulo",
    teacher_course_new_announcement_content: "Contenido",
    teacher_course_button_create_announcement: "Publicar",
    teacher_course_no_announcement: "No hay anuncios a\u00FAn",

    ////////teacher-files_jsp
    teacher_file_section_heading: "Material",
    teacher_file_new_file_title: "Subir nuevo archivo",
    teacher_file_new_file: "Archivo",
    teacher_file_new_file_category: "Categor\u00EDa",
    teacher_file_button_upload_file: "Publicar",

    ////////teacher-exams_jsp
    teacher_exams_section_heading_title: "Ex\u00E1menes",
    teacher_exams_upload_card_title: "Nuevo Examen",
    teacher_exams_upload_exam_title_field: "T\u00EDtulo",
    teacher_exams_upload_exam_instructions_field: "Consigna",
    teacher_exams_upload_exam_attach: "Archivo adjunto",
    teacher_exams_upload_exam_start_date: "Comienzo:",
    teacher_exams_upload_exam_enda_date: "Fin:",
    teacher_exams_recent_exams_title: "\u00DAltimos ex\u00E1menes:",
    teacher_exams_none_yet: "No hay ex\u00E1menes a\u00FAn",
    teacher_exams_button_create_announcement: "Publicar",

    ////////solve-exam_jsp
    solve_exam_description: "Consigna:",
    solve_exam_upload_solution: "Subir soluci\u00F3n",
    solve_exam_cancel_submission: "Cancelar env\u00EDo",
    solve_exam_submit: "Enviar",
    solve_exam_time: "Tiempo restante:",


    ////////////////////////// ADMIN //////////////////////////

    ////////add-user-to-course_jsp
    add_user_page_title: "Campus - Nuevo Usuario",
    add_user_to_course: "Agregar usuarios a {{0}} [{{1}}]",
    add_user_label_user: "Usuario",
    add_user_name_and_surname: "{{0}} - {{1}} {{2}}",
    add_user_label_role: "Rol",
    add_user_role_name: "{{0}}",
    role_Student: "Alumno",
    role_Teacher: "Profesor",
    role_Assistant: "Ayudante de trabajos pr\u00E1cticos",
    add_user_button_add: "Agregar",
    add_user_students: "Alumnos",
    add_user_teachers: "Profesores",
    add_user_helpers: "Ayudantes",

    add_user_img_alt_back: "Bot\u00F3n volver",

    ////////admin-portal_jsp
    ////////admin-sections-col_jsp
    admin_page_title: "Campus - Admin",
    admin_page_header: "Centro de administraci\u00F3n de Campus",
    admin_button_create_user: "Crear nuevo usuario",
    admin_button_create_course: "Crear nuevo curso",
    admin_button_add_user_to_course: "Agregar usuarios a curso",
    admin_button_all_courses: " Ver todos los cursos",

    ////////new-course_jsp
    new_course_page_title: "Campus - Nuevo Curso",
    new_course_header: "Crear nuevo curso",
    new_course_subject: "Materia",
    new_course_quarter: "Cuatrimestre",
    new_course_year: "A\u00F1o",
    new_course_board: "Comisi\u00F3n",
    new_course_button_create: "Crear",
    new_course_duplicated: "Este curso ya existe",

    ////////new-user_jsp
    new_user_page_title: "Campus - Nuevo Usuario",
    new_user_header: "Crear nuevo usuario",
    new_user_file_number: "Legajo",
    new_user_name: "Nombre",
    new_user_surname: "Apellido",
    new_user_username: "Nombre de usuario",
    new_user_email: "Email",
    new_user_password: "Contrase\u00F1a",
    new_user_confirmPassword: "Confirmar contrase\u00F1a",
    new_user_button_create: "Crear",
    new_user_duplicated_username: "Este nombre de usuario ya existe",
    new_user_duplicated_email: "Este email ya existe",
    new_user_duplicated_fileNumber: "Este legajo ya existe",

    ////////select-course_jsp
    select_course_page_title: "Campus - Seleccionar Curso",
    select_course_header: "Seleccionar un curso",
    select_course: "Curso",
    select_course_name_board_year_quarter: "{{0}}[{{1}}]-{{2}}/{{3}}Q",
    select_course_button_add_users: "Ir a agregar usuarios",

    //////all-courses_jsp
    all_courses_page_title: "Campus - Todos los cursos",
    all_courses_from: "Todos los cursos del {{0}}/{{1}}Q",
    all_courses_label_year: "A\u00F1o",
    all_courses_year: "{{0}}",
    all_courses_quarter: "Cuatrimestre",
    all_courses_first_quarter: "1",
    all_courses_second_quarter: "2",
    all_courses_button_search: "Buscar",
    all_courses_no_courses: "No hay cursos para este cuatrimestre",
    all_courses_course_code_title: "C\u00F3digo",
    all_courses_course_name_title: "Nombre",
    all_courses_course_board_title: "Comisi\u00F3n",
    all_courses_course_code: "{{0}}",
    all_courses_course_name: "{{0}}",
    all_courses_course_board: "{{0}}",


    ////////////////////////// COMPONENTS //////////////////////////

    ////////course-sections-col_jsp
    course_sections_col_course_name: "({{1}}{{2}}Q) {{3}} - {{0}} - Comisi\u00F3n: {{4}}",
    course_sections_col_announcements: "Anuncios",
    course_sections_col_teachers: "Profesores",
    course_sections_col_files: "Material",
    course_sections_col_exams: "Examenes",
    course_sections_col_schedule: "Horarios",

    ////////file-search_jsp
    file_search_button: "Buscar",
    file_search_by: "Ordenar por",
    file_search_order_by_date: "Fecha de subida",
    file_search_order_by_name: "Nombre",
    file_search_order_by_downloads: "Descargas",
    file_search_order: "De forma",
    file_search_order_asc: "Ascendente",
    file_search_order_desc: "Descendente",
    file_search_type: "Tipo de archivo",
    file_search_type_all: "Todos",
    file_search_type_other: "Otro",
    file_search_type_name: ".{{0}}",
    file_search_category: "Categor\u00EDa",
    file_search_category_all: "Todos",
    file_search_button_clear_filters: "Limpiar filtros",
    file_search_img_alt_toggle_filters: "Felcha para mostrar filtros",
    file_search_placeholder: "Buscar en mi material",
    file_search_filtered_by: "Filtrado por:",
    file_search_filtered_by_categories: "Categorias:",
    file_search_filtered_by_extensions: "Extensiones:",

    ////////footer_jsp
    footer_message: "Campus \u00A9 {{0}} - Todos los derechos reservados",

    ////////navbar_jsp
    navbar_title: "CAMPUS",
    navbar_my_courses: "Mis Cursos",
    navbar_my_announcements: "Mis Anuncios",
    navbar_my_files: "Mi Material",
    navbar_my_timetable: "Mis Horarios",
    navbar_user: "{{0}}",
    navbar_button_logout: "Salir",
    navbar_logout_alert: "\u00BFEst\u00E1s seguro de que quieres cerrar la sesi\u00F3n?",

    ////////file-unit_jsp
    file_unit_file_name: "{{0}}",
    file_unit_file_downloads: "Descargas: {{0}}",

    ////////announcement-unit_jsp
    announcement_unit_announcement_title: "{{0}}",
    announcement_unit_announcement_publisher: "Publicado por: {{0}} {{1}}",
    announcement_unit_announcement_date: "{{0}}",

    ////////student-exam-unit_jsp
    student_exam_unit_published_at_title: "Entregado el: {{0}}",
    student_exam_unit_not_published: "No entregado",
    student_exam_unit_grade: "Nota:",
    alert_student_exam_unit_undo_correction: "\u00BFEst\u00E1s seguro de que quieres borrar la correccion para este examen?",

    ////////correct-specific-exam_jsp
    correct_specific_exam_description: "Descripci\u00F3n:",
    correct_specific_exam_solution: "Soluci\u00F3n:",
    correct_specific_exam_not_done: "No se entrego el examen_",
    correct_specific_exam_grade: "Nota",
    correct_specific_exam_comments: "Comentarios",
    correct_specific_exam_cancel_submission: "Cancelar env\u00EDo",
    correct_specific_exam_submit: "Enviar",


    ////////////////////////// ALERTS //////////////////////////

    ////////announcement-unit_jsp
    alert_announcement_delete: "\u00BFEst\u00E1s seguro de que quieres borrar el anuncio?",

    ////////file-unit_jsp
    alert_file_delete: "\u00BFEst\u00E1s seguro de que quieres borrar el archivo?",

    ////////exam-unit_jsp
    alert_exam_delete: "\u00BFEst\u00E1s seguro de que quieres borrar el examen?",
    exam_unit_average: "Promedio: {{0}}",
    exam_unit_file_name: "{{0}}",
    exam_unit_grade: "Nota:",
    exam_unit_number_of_corrected_exams: "{{0}} corregidos de {{1}}",
    exam_unit_corrections: "Comentarios: {{0}}",
    exam_unit_no_corrections_yet: "No tienes comentarios sobre este examen_",
    exam_unit_toggle_corrections: "Ver comentarios",


    ////////////////////////// SUCCESS MESSAGES //////////////////////////

    admin_success_message: "Usuario creado exitosamente",
    course_success_message: "Curso creado exitosamente",
    user_success_message: "Usuario agregado exitosamente",
    email_success_message: "Email enviado exitosamente",
    announcement_success_message: "Anuncio publicado exitosamente",
    file_success_message: "Archivo creado exitosamente",
    exam_success_message: "Examen creado exitosamente",
    answer_success_message: "Soluci\u00F3n enviada exitosamente",
    answer_solve_success_message: "Soluci\u00F3n corregida exitosamente",

    ////////////////////////// SHARED //////////////////////////

    ////////400_jsp
    ////////403_jsp
    ////////404_jsp
    ////////500_jsp
    back_to_portal_button: "Volver al portal",

    ////////teacher-files_jsp
    ////////file-search_jsp
    category_other: "Otra",
    category_practice: "Practica",
    category_theory: "Teor\u00EDa",
    category_exam: "Examen",
    category_final: "Final",
    category_guide: "Gu\u00EDa",
    category_bylaws: "Reglamento",
    category_schedule: "Cronograma",

    ////////course_jsp
    ////////course-files_jsp
    ////////teachers_jsp
    ////////teacher-course_jsp
    ////////teacher-files_jsp
    page_title_course_subject_name: "Campus - {{0}}",


    ////////course_jsp
    ////////course-files_jsp
    ////////files_jsp
    ////////protal_jsp
    ////////teachers_jsp
    ////////teacher-course_jsp
    ////////teacher-files_jsp
    ////////course-sections-col_jsp
    ////////new-course_jsp
    subject_name: "{{0}}",

    ////////protal_jsp
    ////////sendmail_jsp
    ////////user_jsp
    campus_page_title: "Campus",

    ////////files_jsp
    ////////course-files_jsp
    no_results: "No hay resultados que coincidan con tu b\u00FAsqueda",

    ////////add-user-to-course_jsp
    back_button: "Volver",

    ////////IMAGES ALT MESSAGES
    //announcement-unit_jsp
    img_alt_delete: "Bot\u00F3n borrar",

    //all-courses_jsp
    //teacher-course_jsp
    //teacher-files_jsp
    //files_jsp
    //portal_jsp
    img_alt_next_page: "Bot\u00F3n para pasar a la siguiente p\u00E1gina",

    //add-usert-to-course_jsp
    img_alt_previous_page: "Bot\u00F3n para volver a la p\u00E1gina anterior",

    //portal_jsp
    //teachers_jsp
    img_alt_teacher_icon: "Icono de sombrero de profesor",

    //student-exam-unit_jsp
    img_alt_check: "Bot\u00F3n de correcci\u00F3n",

    //teachers_jsp
    img_alt_mail_icon: "Icono de mail",

    ////////////////////////// MAIL TEMPLATES //////////////////////////

    //new-announcement-notification_html
    mail_new_announcement_title: "Nuevo anuncio en curso",
    mail_new_announcement_published_by: "Publicado por:",

    //student-email-to-teacher_html
    student_message_title: "Nuevo mensaje de alumno",
    student_message_subject: "Un alumno te ha enviado un mensaje",
    student_message_from: "De:",

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

};