export const TRANSLATIONS_EN = {

    //COMPONENTS

    "AdminSectionCol": {
        "title": "Campus Administration",
        "Nuevo usuario": "New user",
        "Nuevo curso": "New course",
        "Agregar usuario a curso": "Add user to course",
        "Ver todos los cursos": "See all courses",
    },

    "BasicPagination": {
        "message": "Page {{currentPage}} of {{maxPage}}",
        "alt": {
            "nextPage": "Next page",
            "beforePage": "Before page",
        }
    },

    "CourseSectionsCol":{
        "Anuncios":"Announcements",
        "Profesores":"Teachers",
        "Archivos":"Files",
        "Examenes":"Exams",
        "Horarios":"Schedule",
    },

    "ExamUnit":{
        "alt":{
            "exam":"Exam",
            "seeCorrections":"View corrections",
            "delete":"Delete"
        },
        "grade":"Grade: {{grade}}",
        "correctedOf":"{{examsSolved}}/{{userCount}} corrected",
    },

    "FileSearcher":{
        "placeholder":{
            "fileName":"Search the name of the file you are looking for",
        },
        "alt":{
            "toggleFilters":"Toggle filters",
        },
        "search":"Search",
        "searchBy":{
            "title":"Search by:",
            "date":"Date",
            "name":"Name",
            "downloads":"Downloads"
        },
        "orderBy": {
            "title":"Order by:",
            "asc":"Ascending",
            "desc":"Descending"
        },
        "fileType": {
            "title": "File types",
            "all": "All",
            "other": "Other"
        },
        "category":{
            "title": "Category",
            "all": "All",
            "other": "Other",
        },
        "clearFilters": "Clear Filters",
        "filteredBy": "Filtered by:"
    },

    "Category":{
        "practice": "Practice",
        "theory": "Theory",
        "exam": "Exam",
        "final": "Final",
        "guide": "Guide",
        "bylaws": "Bylaw",
        "schedule": "Schedule",
    },

    "FileUnit":{
        "alt":{
            "file": "File",
            "delete": "Delete",
        }
    },

    "Footer": "Campus © {{year}} - All rights reserved",

    "Navbar":{
        "sections":{
            "/portal":"My courses",
            "/announcements":"My announcements",
            "/files":"My files",
            "/timetable":"My timetable",
        },
        "logout":"Logout"
    },

    "StudentExamUnit":{
        "alt":{
            "check":"correct",
        },
        "notHandedIn": "Not handed in",
    },

    ////VIEWS

    "404":{
        "title":"Error 404!",
        "backToPortalButton": "Back to portal"
    },

    //Admin

    "AdminAddUserToCourse":{
        "alt":{
            "backButton":"Back",
        },
        "backButton":"Back",
        "addUserToCourse":"Add user to {{subjectName}}[{{courseBoard}}]",
        "form":{
            "user":"User",
            "role":{
                "title":"Role",
                "Student":"Student",
                "Teacher":"Teacher",
                "Assistant":"Helper",
            },
            "addButton":"Add user",
            "students":"Students",
            "teachers":"Teachers",
            "assistants":"Helpers",
        }
    },

    "AdminAllCourses":{
        "allCoursesFrom":"All courses from {{year}}/{{quarter}}",
        "form":{
            "year":"Year",
            "quarter":"Quarter",
            "searchButton":"Search",
        },
        "noCourses":"No courses in this quarter",
        "table":{
            "code":"Code",
            "name":"Name",
            "board":"Board",
        },
    },

    "AdminNewCourse":{
        "newCourse":"New course",
        "days":{
            "Lunes":"Monday",
            "Martes":"Tuesday",
            "Miercoles":"Wednesday",
            "Jueves":"Thursday",
            "Viernes":"Friday",
            "Sabado":"Saturday",
        },
        "form":{
            "subject":"Subject",
            "quarter":"Quarter",
            "year":"Year",
            "board":"Board",
            "createButton":"Create"
        },
        "error":{
            "alreadyExist":"This course already exist",
        },
    },

    "AdminNewUser":{
        "error":{
            "passwordsMustMatch":"Passwords must match",
            "fileNumber":{
                "isRequired":"File number is required",
                "positiveInteger":"File number must be a positive integer",
                "exists":"File number already exist",
            },
            "name":{
                "onlyLetters":"Name must only contain letters",
                "isRequired":"Name field is required",
            },
            "surname": {
                "onlyLetters":"Surname must only contain letters",
                "isRequired":"Surname field is required",
            },
            "username": {
                "pattern":"Username must only contain letters and numbers",
                "isRequired":"Username field is required",
                "length":"Username must have between 6 and 50 characters",
                "exists":"Username already exist",
            },
            "email":{
                "pattern":"Please enter a valid email",
                "isRequired":"Email field is required",
                "exists":"This email is already in use",
            },
            "password":{
                "pattern":"Password must contain a capital letter, a lower case and a number",
                "isRequired":"Password field is required",
                "length":"Password must have between 8 and 50 characters",
            },

        },
        "form":{
            "title":"Create new user",
            "fileNumber":"File number",
            "name":"Name",
            "surname":"Surname",
            "username":"Username",
            "email":"Email",
            "password":"Password",
            "confirmPassword":"Confirm password",
            "createButton":"Create"
        },

    },

    "AdminPortal":{
        "title":"Campus administration center",
        "createNewUserButton":"Create new user",
        "createNewCourseButton":"Create new course",
        "addUserToCourseButton":"Add user to course",
        "seeAllCoursesButton":"See all courses",
    },

    "AdminSelectCourse":{
        "title":"Select a course",
        "form":{
            "course":"Course",
            "selectButton":"Select",
        },
    },

    "Announcements":{
        "title":"Announcements",
        "noAnnouncements":"You have no announcements yet!",
    },

    "CourseAnnouncements":{
        "teacher":{
            "form":{
                "title":"New Announcement",
                "announcementTitleLabel":"Title",
                "announcementContentLabel":"Content",
                "announcementCreateButton":"Create",
            },
            "error":{
                "title":{
                    "isRequired":"Title field is required",
                    "length":"The title must have between 2 and 50 characters",
                },
                "content":{
                    "isRequired":"The announcement content can not be empty",
                    "length":"Content must be grater than 2 characters",
                }
            },
        },
    },

    "CorrectExam":{
        "descriptionTitle":"Description:",
        "solutionTitle":"Solution:",
        "examNotDone":"Exam not done",
        "form":{
            "title":"Correct exam",
            "comments": "Comments",
            "submitButton": "Correct",
            "cancelCorrectionButton":"Cancel correction"

        },
        "error":{
            "grade":{
                "isRequired":"Grade can not be empty",
                "minGrade":"Minimum grade is 0",
                "maxGrade":"Maximum grade is 10",
            },
            "comments":{
                "length":"Comments can not be longer than 50 characters",
            },
        },
    },

    "StudentCourseExamStandalone":{
        "timeLeft": "Time left: {{days}}d:{{hours}}h:{{minutes}}m:{{seconds}}s",
        "examDescriptionTitle":"Description:",
        "form":{
            "solutionTitle":"Solution",
            "cancelSend":"Cancel",
            "send":"Send",
        },
        "error":{
            "file":{
                "isRequired":"Attach a file",
                "size":"File must be smaller than 50mb",
            },
        },
    },

    ////////////////////////////////////////////////////// ERROR MESSAGES //////////////////////////////////////////////////////

    ////////AnnouncementForm error messages

    Size_announcementForm_title: "The announcement title must contain at least {{2}} characters and a maximum of {{1}}.",
    Size_announcementForm_content: "The announcement content must contain at least {{2}} characters.",

    ////////MailForm error messages

    NotBlank_mailForm_subject: "Email subject can not be empty.",
    NotBlank_mailForm_content: "Email content can not be empty.",

    ////////FileForm error messages

    NotNull_fileForm_file: "A file must be attached.",
    MaxFileSize_fileForm_file: "File must be smaller than {{1}}MB.",
    NotNull_fileForm_categoryId: "The file must have a category.",

    ////////EmailForm error messages

    Email_emailForm: "Please enter a valid email.",

    ////////FileForm error messages

    NotEmptyFile_fileForm_file: "A file must be attached.",

    ////////CreateExamForm error messages

    Size_createExamForm_title: "The exam title must contain at least {{2}} characters and a maximum of {{1}}.",
    Size_createExamForm_content: "The exam instructions must contain at least {{2}} characters and a maximum of {{1}}.",
    NotEmptyFile_createExamForm_file: "A file must be included.",
    MaxFileSize_createExamForm_file: "File must be smaller than {{1}}MB.",
    NotNull_createExamForm_startTime: "The exam must contain start time and date.",
    NotBlank_createExamForm_startTime: "Start time and date can not be empty.",
    NotNull_createExamForm_endTime: "End time must come after the start time.",
    NotBlank_createExamForm_endTime: "End time and date can not be empty.",

    ////////SolveExamForm error messages

    NotEmptyFile_solveExamForm_exam: "A file must be included.",
    MaxFileSize_solveExamForm_exam: "File must be smaller than {{1}}MB.",

    ////////AnswerCorrectionForm error messages
    Size_answerCorrectionForm_comments: "Comments can only have between {{2}} and {{1}} characters.",
    NotNull_answerCorrectionForm_mark: "Grade field can not be empty.",
    Max_answerCorrectionForm_mark: "Maximum grade is {{1}}.",
    Min_answerCorrectionForm_mark: "Minimum grade is {{1}}.",

    ////////UserProfileForm
    NotEmptyFile_userProfileForm_image: "An image must be included.",
    MaxFileSize_userProfileForm_image: "Image must be smaller than {{1}}MB.",

    ////////////////////////// ADMIN ERRORS //////////////////////////

    ////////UserRegisterForm error messages

    Min_userRegisterForm_fileNumber: "File number must be higher than 0.",
    Max_userRegisterForm_fileNumber: "File number too big.",
    typeMismatch_userRegisterForm_fileNumber: "File number field can not be empty.",
    NotNull_userRegisterForm_fileNumber: "File number field can not be empty.",
    Pattern_userRegisterForm_name: "The name must only contain letters.",
    NotBlank_userRegisterForm_name: "Name field can not be empty.",
    Pattern_userRegisterForm_surname: "The surname must only contain letters.",
    NotBlank_userRegisterForm_surname: "Surname field can not be empty.",
    Size_userRegisterForm_username: "Username must contain between {{2}} and {{1}} characters.",
    NotBlank_userRegisterForm_username: "Username field can not be empty.",
    Pattern_userRegisterForm_username: "Username must only contain letters or numbers.",
    Email_userRegisterForm_email: "Please enter a valid email.",
    Size_userRegisterForm_email: "Email field can not be empty.",
    NotBlank_userRegisterForm_email: "Email field can not be empty.",
    Pattern_userRegisterForm_password: "Password must contain at least: one lowercase letter, one uppercase letter and a number.",
    Size_userRegisterForm_password: "Password must contain between {{2}} and {{1}} characters.",
    NotBlank_userRegisterForm_password: "Password field can not be empty.",
    NotBlank_userRegisterForm_confirmPassword: "Confirm password field can not be empty and must match the password entered.",
    NotNull_userRegisterForm_confirmPassword: "Passwords do not match.",

    ////////CourseForm error messages
    Min_courseForm_quarter: "Quarter must be higher than {{1}}.",
    Max_courseForm_quarter: "Quarter too big.",
    NotNull_courseForm_quarter: "Quarter field can not be empty.",
    NotNull_courseForm_board: "Board field can not be empty.",
    NotBlank_courseForm_board: "Board field can not be empty.",
    Length_courseForm_board: "Board max length is {{1}} characters.",
    Min_courseForm_year: "Year must be higher than {{1}}.",
    NotNull_courseForm_year: "Year field can not be empty.",


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////// FRONT-END STRINGS ////////////////////////////////////////////////////

    ////////403_jsp
    _403_page_title: "Campus - Error",
    _403_page_message: "403 : Access Denied",

    ////////404_jsp
    _404_page_title: "Campus - Error",
    _404_page_message: "404 : We can not find what you are looking for",

    ////////400_jsp
    _400_page_title: "Campus - Error",
    _400_page_message: "400 : Resource not found",

    ////////500_jsp
    _500_page_title: "Campus - Error",
    _500_page_message: "500 : Internal server error",

    ////////announcements_jsp
    announcements_page_title: "Campus - Announcements",
    announcements_section_heading_title: "My Announcements",
    announcement_title: "{{0}}",
    announcement_no_announcement: "No announcements in this course",
    page_actual: "Page {{0}} of {{1}}",

    ////////course_jsp
    course_section_heading_title: "Announcements",

    ////////course-files_jsp
    course_file_section_heading_title: "Files",

    ////////error-page_jsp
    errorPage_page_title: "Campus - Error",
    errorPage_message: "{{0}}",

    ////////files_jsp
    files_page_title: "Campus - Files",
    files_section_heading_title: "My Files",

    ////////login_jsp
    login_page_title: "Campus - Login",
    login_section_heading_title: "Log in",
    login_label_user: "Username",
    login_label_password: "Password",
    login_label_remember_me: "Remember me",
    login_bad_credentials: "Invalid credentials",
    login_button_login: "Log in",

    ////////protal_jsp
    portal_section_heading_title: "My Courses",
    portal_subject_board_name: "{{0}} - Board: {{1}}",
    portal_course_info: "{{0}}/{{1}}Q",
    portal_last_announcements: "Last Announcements",

    ////////sendmail_jsp
    sendmail_title_to: "Send mail to {{0}} {{1}}",
    sendmail_label_subject: "Subject",
    sendmail_label_content: "Content",
    sendmail_button_send: "Send",

    ////////teachers_jsp
    teachers_section_heading_title: "Teachers",
    teachers_teacher_name: "{{0}} {{1}}",
    teachers_teacher_email: "{{0}}",
    teachers_mail_icon_hover_text: "Send email_",

    ////////course-schedule_jsp
    course_schedule_section_heading_title: "Schedule",
    course_schedule_comment: "This is your class schedule, ask an authorized teacher in case of any changes.",

    ////////course_exams_jsp
    course_exams_section_heading_title: "Exams",
    course_exams_comment: "Exams to do:",
    course_exams_no_exams: "There are no exams to do!",
    course_exams_sent_exams: "Sent exams",
    course_exams_sent_average: "Average: {{0}}",

    ////////timetable_jsp
    timetable_page_title: "Campus - Timetable",
    timetable_section_heading_title: "My Timetable",
    day_Monday: "Monday",
    day_Tuesday: "Tuesday",
    day_Wednesday: "Wednesday",
    day_Thursday: "Thursday",
    day_Friday: "Friday",
    day_Saturday: "Saturday",

    ////////user_jsp
    user_name: "{{0}} {{1}}",
    user_username_title: "Username:",
    user_username: "{{0}}",
    user_email_title: "Email:",
    user_email: "{{0}}",
    user_filenumber_title: "File number:",
    user_filenumber: "{{0}}",
    user_insert_image_title: "Insert image",
    user_insert_image_button: "Confirm",

    ////////correct-exam_jsp
    teacher_correct_exam_none_to_correct: "There are no exams to correct",
    teacher_correct_exam_none: "There are no results",
    teacher_correct_exam_filter_by: "Filter by:",
    teacher_correct_exam_filter_all: "All",
    teacher_correct_exam_filter_corrected: "Corrected",
    teacher_correct_exam_filter_not_corrected: "Not corrected",
    teacher_correct_exam_filter: "Filter",
    teacher_correct_exam_average: "Average: {{0}}",

    ////////teacher-course_jsp
    teacher_course_section_heading: "Announcements",
    teacher_course_new_announcement: "Create new announcement",
    teacher_course_new_announcement_title: "Title",
    teacher_course_new_announcement_content: "Content",
    teacher_course_button_create_announcement: "Publish",
    teacher_course_no_announcement: "No announcements in this course",


    ////////teacher-files_jsp
    teacher_file_section_heading: "Files",
    teacher_file_new_file_title: "Upload new file",
    teacher_file_new_file: "File",
    teacher_file_new_file_category: "Category",
    teacher_file_button_upload_file: "Publish",

    ////////teacher-exams_jsp
    teacher_exams_section_heading_title: "Exams",
    teacher_exams_upload_card_title: "New Exam",
    teacher_exams_upload_exam_title_field: "Title",
    teacher_exams_upload_exam_instructions_field: "Instructions",
    teacher_exams_upload_exam_attach: "Attachment",
    teacher_exams_upload_exam_start_date: "Start:",
    teacher_exams_upload_exam_enda_date: "Finish:",
    teacher_exams_recent_exams_title: "Recent exams:",
    teacher_exams_none_yet: "No exams yet",
    teacher_exams_button_create_announcement: "Publish",

    ////////solve-exam_jsp
    solve_exam_description: "Description:",
    solve_exam_upload_solution: "Upload solution",
    solve_exam_cancel_submission: "Cancel submission",
    solve_exam_submit: "Submit",
    solve_exam_time: "Time left:",

    ////////////////////////// ADMIN //////////////////////////

    ////////add-user-to-course_jsp
    add_user_page_title: "Campus - New User",
    add_user_to_course: "Enroll user to {{0}} [{{1}}]",
    add_user_label_user: "User",
    add_user_name_and_surname: "{{0}} - {{1}} {{2}}",
    add_user_label_role: "Role",
    add_user_role_name: "{{0}}",
    role_Student: "Student",
    role_Teacher: "Teacher",
    role_Assistant: "Assistant",
    add_user_button_add: "Enroll",
    add_user_students: "Students",
    add_user_teachers: "Teachers",
    add_user_helpers: "Helpers",
    add_user_img_alt_back: "Back button",

    ////////admin-portal_jsp
    admin_page_title: "Campus - Admin",
    admin_page_header: "Campus Administration Center",
    admin_button_create_user: "Create new user",
    admin_button_create_course: "Create course",
    admin_button_add_user_to_course: "Enroll users to course",
    admin_button_all_courses: " See all courses",

    ////////new-course_jsp
    new_course_page_title: "Campus - New Course",
    new_course_header: "Create new course",
    new_course_subject: "Subject",
    new_course_quarter: "Quarter",
    new_course_year: "Year",
    new_course_board: "Board",
    new_course_button_create: "Create",
    new_course_duplicated: "This course already exists",

    ////////new-user_jsp
    new_user_page_title: "Campus - New User",
    new_user_header: "Create new user",
    new_user_file_number: "File Number",
    new_user_name: "Name",
    new_user_surname: "Surname",
    new_user_username: "Username",
    new_user_email: "Email",
    new_user_password: "Password",
    new_user_confirmPassword: "Confirm password",
    new_user_button_create: "Create",
    new_user_duplicated_username: "This username already exists",
    new_user_duplicated_email: "This email already exists",
    new_user_duplicated_fileNumber: "This file number already exists",

    ////////select-course_jsp
    select_course_page_title: "Campus - Select Course",
    select_course_header: "Select a course",
    select_course: "Course",
    select_course_name_board_year_quarter: "{{0}}[{{1}}]-{{2}}/{{3}}Q",
    select_course_button_add_users: "Enroll users",

    //////all-courses_jsp
    all_courses_page_title: "Campus - All courses",
    all_courses_from: "All courses from {{0}}/{{1}}Q",
    all_courses_label_year: "Year",
    all_courses_year: "{{0}}",
    all_courses_quarter: "Quarter",
    all_courses_first_quarter: "1",
    all_courses_second_quarter: "2",
    all_courses_button_search: "Search",
    all_courses_no_courses: "There are no courses in this quarter",
    all_courses_course_code_title: "Code",
    all_courses_course_name_title: "Name",
    all_courses_course_board_title: "Board",
    all_courses_course_code: "{{0}}",
    all_courses_course_name: "{{0}}",
    all_courses_course_board: "{{0}}",

    ////////////////////////// COMPONENTS //////////////////////////

    ////////course-sections-col_jsp
    course_sections_col_course_name: "({{1}}{{2}}Q) {{3}} - {{0}} - Board: {{4}}",
    course_sections_col_announcements: "Announcements",
    course_sections_col_teachers: "Teachers",
    course_sections_col_files: "Files",
    course_sections_col_exams: "Exams",
    course_sections_col_schedule: "Schedule",

    ////////file-search_jsp
    file_search_button: "Search",
    file_search_by: "Order by",
    file_search_order_by_date: "Upload date",
    file_search_order_by_name: "Name",
    file_search_order_by_downloads: "Downloads",
    file_search_order: "By",
    file_search_order_asc: "Ascending",
    file_search_order_desc: "Descending",
    file_search_type: "File type",
    file_search_type_all: "All",
    file_search_type_other: "Other",
    file_search_type_name: "_{{0}}",
    file_search_category: "Category",
    file_search_category_all: "All",
    file_search_button_clear_filters: "Clear filters",
    file_search_img_alt_toggle_filters: "Toggle filters arrow",
    file_search_placeholder: "Search in your files",
    file_search_filtered_by: "Filtered by:",
    file_search_filtered_by_categories: "Categories:",
    file_search_filtered_by_extensions: "Extensions:",

    ////////footer_jsp
    footer_message: "Campus \u00A9 {{0}} - All rights reserved",

    ////////navbar_jsp
    navbar_title: "CAMPUS",
    navbar_my_courses: "My Courses",
    navbar_my_announcements: "My Announcements",
    navbar_my_files: "My Files",
    navbar_my_timetable: "My Timetable",
    navbar_user: "{{0}}",
    navbar_button_logout: "Exit",
    navbar_logout_alert: "Are you sure you want to logout?",

    ////////file-unit_jsp
    file_unit_file_name: "{{0}}",
    file_unit_file_downloads: "Downloads: {{0}}",

    ////////announcement-unit_jsp
    announcement_unit_announcement_title: "{{0}}",
    announcement_unit_announcement_publisher: "Published by: {{0}} {{1}}",
    announcement_unit_announcement_date: "{{0}}",

    ////////student-exam-unit_jsp
    student_exam_unit_published_at_title: "Published at: {{0}}",
    student_exam_unit_not_published: "Not published",
    student_exam_unit_grade: "Grade:",
    alert_student_exam_unit_undo_correction: "Are you sure you want to undo this correction?",

    ////////correct-specific-exam_jsp
    correct_specific_exam_description: "Description:",
    correct_specific_exam_solution: "Solution:",
    correct_specific_exam_not_done: "Exam not done_",
    correct_specific_exam_grade: "Grade",
    correct_specific_exam_comments: "Comments",
    correct_specific_exam_cancel_submission: "Cancel submission",
    correct_specific_exam_submit: "Submit",


    ////////////////////////// ALERTS //////////////////////////

    ////////announcement-unit_jsp
    alert_announcement_delete: "Are you sure you want to delete the announcements?",

    ////////file-unit_jsp
    alert_file_delete: "Are you sure you want to delete the file?",

    ////////exam-unit_jsp
    alert_exam_delete: "Are you sure you want to delete the exam?",
    exam_unit_average: "Average: {{0}}",
    exam_unit_file_name: "{{0}}",
    exam_unit_grade: "Grade:",
    exam_unit_number_of_corrected_exams: "{{0}} corrected of {{1}}",
    exam_unit_corrections: "Corrections: {{0}}",
    exam_unit_no_corrections_yet: "No comments for this exam",
    exam_unit_toggle_corrections: "Toggle corrections",

    ////////////////////////// SUCCESS MESSAGES //////////////////////////

    admin_success_message: "User created successfully",
    course_success_message: "Course created successfully",
    user_success_message: "User created successfully",
    email_success_message: "Email sent successfully",
    announcement_success_message: "Announcement created successfully",
    file_success_message: "File uploaded successfully",
    exam_success_message: "Exam created successfully",
    answer_success_message: "Solution sent successfully",
    answer_solve_success_message: "Solution corrected successfully",

    ////////////////////////// SHARED //////////////////////////

    ////////400_jsp
    ////////403_jsp
    ////////404_jsp
    ////////500_jsp
    back_to_portal_button: "Back to portal",

    ////////teacher-files_jsp
    ////////file-search_jsp
    category_other: "Other",
    category_practice: "Practice",
    category_theory: "Theory",
    category_exam: "Exam",
    category_final: "Final",
    category_guide: "Guide",
    category_bylaws: "Bylaw",
    category_schedule: "Schedule",

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
    subject_name: "{{0}}",

    ////////protal_jsp
    ////////sendmail_jsp
    ////////user_jsp
    campus_page_title: "Campus",

    ////////files_jsp
    ////////course-files_jsp
    no_results: "No results matching query.",

    ////////add-user-to-course_jsp
    back_button: "Back",


    ////////////////////////// IMAGES ALT MESSAGES //////////////////////////

    //announcement-unit_jsp
    //file-unit_jsp
    img_alt_delete: "Delete button",

    //all-courses_jsp
    //teacher-course_jsp
    //teacher-files_jsp
    //files_jsp
    //portal_jsp
    img_alt_next_page: "Next page button",

    //add-usert-to-course_jsp
    img_alt_previous_page: "Before page button",

    //portal_jsp
    //teachers_jsp
    img_alt_teacher_icon: "Professor icon",

    //student-exam-unit_jsp
    img_alt_check: "Check button",

    //teachers_jsp
    img_alt_mail_icon: "Mail icon",

    ////////////////////////// MAIL TEMPLATES //////////////////////////

    //new-announcement-notification_html
    mail_new_announcement_title: "New announcement in course",
    mail_new_announcement_published_by: "Published by:",

    //student-email-to-teacher_html
    student_message_title: "New student message",
    student_message_subject: "A student sent you a message",
    student_message_from: "From:",

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
};