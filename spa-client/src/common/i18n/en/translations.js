export const TRANSLATIONS_EN = {
  DaysOfTheWeek: {
    Lunes: "Monday",
    Martes: "Tuesday",
    Miercoles: "Wednesday",
    Jueves: "Thursday",
    Viernes: "Friday",
    Sabado: "Saturday",
  },

  //COMPONENTS

  AdminSectionCol: {
    title: "Campus Administration",
    "Nuevo usuario": "New user",
    "Nuevo curso": "New course",
    "Agregar usuario a curso": "Add user to course",
    "Ver todos los cursos": "See all courses",
  },

  AnnouncementUnit: {
    author: "Author: {{name}} {{surname}}",
    subject: "Subject: {{subjectName}}",
    alt: {
      deleteButton: "Delete"
    },
  },

  BasicPagination: {
    message: "Page {{currentPage}} of {{maxPage}}",
    alt: {
      nextPage: "Next page",
      beforePage: "Before page",
    },
  },

  CourseSectionsCol: {
    Anuncios: "Announcements",
    Profesores: "Teachers",
    Archivos: "Files",
    Examenes: "Exams",
    Horarios: "Schedule",
  },

  ExamUnit: {
    alt: {
      exam: "Exam",
      seeCorrections: "View corrections",
      delete: "Delete",
    },
    grade: "Grade: {{grade}}",
    correctedOf: "{{examsSolved}}/{{userCount}} corrected",
  },

  FileSearcher: {
    placeholder: {
      fileName: "Search the name of the file you are looking for",
    },
    alt: {
      toggleFilters: "Toggle filters",
    },
    search: "Search",
    searchBy: {
      title: "Search by:",
      date: "Date",
      name: "Name",
      downloads: "Downloads",
    },
    orderBy: {
      title: "Order by:",
      asc: "Ascending",
      desc: "Descending",
    },
    fileType: {
      title: "File types",
      all: "All",
      other: "Other",
    },
    category: {
      title: "Category",
      all: "All",
      other: "Other",
    },
    clearFilters: "Clear Filters",
    filteredBy: "Filtered by:",
  },

  Category: {
    practice: "Practice",
    theory: "Theory",
    exam: "Exam",
    final: "Final",
    guide: "Guide",
    bylaws: "Bylaw",
    schedule: "Schedule",
    other: "Other",
  },

  FileUnit: {
    alt: {
      file: "File",
      delete: "Delete",
    },
    downloads: "Downloads: {{downloads}}",
  },

  Footer: "Campus © {{year}} - All rights reserved",

  Navbar: {
    sections: {
      "/portal": "My courses",
      "/announcements": "My announcements",
      "/files": "My files",
      "/timetable": "My timetable",
    },
    logout: "Logout",
  },

  StudentExamUnit: {
    alt: {
      check: "correct",
    },
    notHandedIn: "Not handed in",
  },

  ////VIEWS

  404: {
    title: "Error 404!",
    backToPortalButton: "Back to portal",
  },

  //Admin

  AdminAddUserToCourse: {
    alt: {
      backButton: "Back",
    },
    backButton: "Back",
    addUserToCourse: "Add user to {{subjectName}}[{{courseBoard}}]",
    form: {
      user: "User",
      role: {
        title: "Role",
        Student: "Student",
        Teacher: "Teacher",
        Assistant: "Helper",
      },
      addButton: "Add user",
      students: "Students",
      teachers: "Teachers",
      assistants: "Helpers",
    },
    toast: {
      error:{
        userNotAdded: "User not added, please try again",
      },
    },
  },

  AdminAllCourses: {
    allCoursesFrom: "All courses from {{year}}/{{quarter}}Q",
    form: {
      year: "Year",
      quarter: "Quarter",
      searchButton: "Search",
    },
    noCourses: "No courses in this quarter",
    table: {
      code: "Code",
      name: "Name",
      board: "Board",
    },
  },

  AdminNewCourse: {
    newCourse: "New course",
    form: {
      subject: "Subject",
      quarter: "Quarter",
      year: "Year",
      board: "Board",
      createButton: "Create",
    },
    error: {
      alreadyExist: "This course already exist",
    },
    toast:{
      error:{
        notCreated: "Course could not be created, please try again"
      },
      message:{
        createdCorrectly: "👑 Course created successfully!",
      },
    },
  },

  AdminNewUser: {
    error: {
      passwordsMustMatch: "Passwords must match",
      fileNumber: {
        isRequired: "File number is required",
        positiveInteger: "File number must be a positive integer",
        exists: "File number already exist",
      },
      name: {
        onlyLetters: "Name must only contain letters",
        isRequired: "Name field is required",
      },
      surname: {
        onlyLetters: "Surname must only contain letters",
        isRequired: "Surname field is required",
      },
      username: {
        pattern: "Username must only contain letters and numbers",
        isRequired: "Username field is required",
        length: "Username must have between 6 and 50 characters",
        exists: "Username already exist",
      },
      email: {
        pattern: "Please enter a valid email",
        isRequired: "Email field is required",
        exists: "This email is already in use",
      },
      password: {
        pattern:
          "Password must contain a capital letter, a lower case and a number",
        isRequired: "Password field is required",
        length: "Password must have between 8 and 50 characters",
      },
      repeated: "Username, email and file-number must be unique",
    },
    form: {
      title: "Create new user",
      fileNumber: "File number",
      name: "Name",
      surname: "Surname",
      username: "Username",
      email: "Email",
      password: "Password",
      confirmPassword: "Confirm password",
      createButton: "Create",
    },
    toast:{
      error:{
        notCreated: "User could not be created, please try again"
      },
      message:{
        createdCorrectly: "👑 User created successfully!",
      },
    },
  },

  AdminPortal: {
    title: "Campus administration center",
    createNewUserButton: "Create new user",
    createNewCourseButton: "Create new course",
    addUserToCourseButton: "Add user to course",
    seeAllCoursesButton: "See all courses",
  },

  AdminSelectCourse: {
    title: "Select a course",
    form: {
      course: "Course",
      selectButton: "Select",
    },
  },

  Announcements: {
    title: "Announcements",
    noAnnouncements: "You have no announcements yet!",
  },

  CourseAnnouncements: {
    teacher: {
      form: {
        title: "New Announcement",
        announcementTitleLabel: "Title",
        announcementContentLabel: "Content",
        announcementCreateButton: "Create",
      },
      error: {
        title: {
          isRequired: "Title field is required",
          length: "The title must have between 2 and 50 characters",
        },
        content: {
          isRequired: "The announcement content can not be empty",
          length: "Content must be grater than 2 characters",
        },
      },
      alert:{
        deleteAnnouncement: "Are you sure you want to delete this announcement?",
      },
      toast:{
        error:{
          notRemoved: "Announcement could not be removed, please try again",
          notCreated: "Announcement could not be created, please try again"
        },
        message:{
          removedCorrectly: "👑 Announcement removed successfully!",
          createdCorrectly: "👑 Announcement created successfully!",
        },
      },
    },
  },

  CorrectExam: {
    descriptionTitle: "Description:",
    solutionTitle: "Solution:",
    examNotDone: "Exam not done",
    form: {
      title: "Correct exam",
      comments: "Comments",
      submitButton: "Correct",
      cancelCorrectionButton: "Cancel correction",
    },
    error: {
      grade: {
        isRequired: "Grade can not be empty",
        minGrade: "Minimum grade is 0",
        maxGrade: "Maximum grade is 10",
      },
      comments: {
        length: "Comments can not be longer than 50 characters",
      },
    },
    toast:{
      error:{
        notCorrected: "The exam could not be corrected, please try again",
      },
      message:{
        correctedCorrectly: "👑 Exam corrected with {{mark}}",
      },
    },
  },

  StudentCourseExamStandalone: {
    timeLeft: "Time left: {{days}}d:{{hours}}h:{{minutes}}m:{{seconds}}s",
    examDescriptionTitle: "Description:",
    form: {
      solutionTitle: "Solution",
      cancelSend: "Cancel",
      send: "Send",
    },
    error: {
      file: {
        isRequired: "Attach a file",
        size: "File must be smaller than 50mb",
      },
    },
    toast:{
      error:{
        notSent: "The exam could not be sent, please try again",
      },
      message:{
        sentCorrectly: "👑 Exam sent successfully!",
      },
    },
  },

  TeacherCourseExamStandalone: {
    filteredBy: "Filter by",
    filters: {
      all: "All",
      corrected: "Corrected",
      notCorrected: "Not corrected",
    },
    filterButton: "Filter",
    noExams: "No exams that satisfy criteria",
    toast:{
      error:{
        notUndoCorrection: "The exam could not be uncorrected, please try again",
      },
      message:{
        undoCorrectly: "👑 The exam was successfully uncorrected",
      },
    },
    alert: {
      undoCorrection: "Are you sure you want to delete this exam correction?"
    },
  },

  StudentExams: {
    title: "Exams",
    toDo: "Available exams:",
    noExams: "No exams available",
    sentExams: "Exams done",
  },

  TeacherExams: {
    title: "Exams",
    createExam: "Create exam",
    form: {
      examTitle: "Title",
      examInstructions: "Instructions",
      examFile: "File",
      examStart: "Beginning",
      examEnd: "End",
      createButton: "Create",
    },
    error: {
      examTitle: {
        isRequired: "The title of the exam is required",
        length: "The title must have between 2 and 50 characters",
      },
      examInstructions: {
        isRequired: "The instructions are required",
        length: "The instructions must have between 2 and 50 characters",
      },
      examFile: {
        isRequired: "A file must be attached",
        size: "File must be smaller than 50mb",
      },
      examStart: {
        isRequired: "The start time is required",
        badDate: "Start time must be before end time",
      },
      examEnd: {
        isRequired: "The end time is required",
      },
    },
    recentExams: "Recent exams",
    noExams: "No exams",
    toast:{
      error:{
        notCreated: "The exam could not be created, please try again",
        notRemoved: "The exam could not be removed, please try again",
      },
      message:{
        createdCorrectly: "👑 Exam created successfully!",
        removedCorrectly: "👑 Exam deleted successfully!",
      },
    },
    alert: {
      deleteExam: "Are you sure you want to delete this exam?",
    },
  },

  CourseFiles: {
    teacher: {
      form: {
        title: "New File",
        file: "File",
        category: "Category",
        uploadFileButton: "Upload File",
      },
      error: {
        file: {
          isRequired: "A file must be attached",
          size: "File must be smaller than 50mb",
        },
      },
    },
    title: "Files",
    noResults: "No results!",
    toast:{
      error:{
        notUploaded: "The file could not be uploaded, please try again",
        notRemoved: "The file could not be removed, please try again",
      },
      message:{
        uploadedCorrectly: "👑 File uploaded correctly!",
        removedCorrectly: "👑 File deleted successfully!",
      },
    },
    alert: {
      deleteFile: "Are you sure you want to delete this file?",
    },
  },

  CourseSchedule: {
    title: "Schedule",
    subTitle: "This is the timetable of the course.",
  },

  CourseTeachers: {
    title: "Teachers",
    alt: {
      mail: "Mail",
      title: "Send email",
    },
  },

  Mail: {
    form: {
      title: "Send email to {{name}} {{surname}}",
      subject: "Subject",
      message: "Message",
      sendMailButton: "Send",
    },
    error: {
      subject: {
        isRequired: "A subject is required",
      },
      message: {
        isRequired: "A content is required",
      },
    },
    toast:{
      error:{
        notSent: "The email could not be sent, please try again",
      },
      message:{
        sentCorrectly: "👑 Email sent successfully!",
      },
    },
  },

  Files: {
    title: "Files",
    noResults: "No results!",
  },

  Login: {
    title: "Login",
    form: {
      user: "Username",
      password: "Password",
      rememberMe: "Remember me",
      loginButton: "Login",
    },
    error: {
      user: {
        isRequired: "Enter a username",
        badUser: "This username is not valid",
      },
      password: {
        isRequired: "Enter a password",
        badPassword: "Incorrect password",
      },
      invalidCredentials: "Invalid credentials",
    },
  },

  Portal: {
    title: "Courses",
    lastAnnouncements: "Last Announcements",
    noAnnouncements: "No announcements to see!",
  },

  Timetable: {
    title: "My Timetable",
  },

  User: {
    form: {
      title: "Insert image",
      confirmButton: "Confirm",
    },
    error: {
      file: {
        isRequired: "An image must be attached",
        size: "Image must be smaller than 5mb.",
      },
    },
    usernameTitle: "Username: ",
    emailTitle: "Email: ",
    fileNumberTitle: "File number: ",
    toast:{
      error:{
        notChanged: "The image could not be changed, please try again later",
      },
      message:{
        changedCorrectly: "👑 Image changed successfully!",
      },
    },
  },
};
