import { IResult, LogIn, User } from "./global-interfaces"

export const defaultResult = {
    isLoading: false,
    isLoaded: false,
    isError: false,
    data: undefined,
    error: undefined
} as IResult


export const errorResult = {
    isLoading: false,
    isLoaded: false,
    isError: true,
    data: undefined,
    error: "une erreur est survenue"
} as IResult

export const successResult = {
    isLoading: false,
    isLoaded: false,
    isError: false,
    data: { data: "On a trouvé des données" },
    success: "succes",
    error: undefined
} as IResult

export const mockLogin = {
    user: {},
    token: "dedeedede",
    createAt: new Date().toString(),
    expireAt: null
} as LogIn;


export const mockBabySitter = {
    firstname: "Wilnie",
    lastname: "JULME"
} as User;

export const mockBaby = {
    firstname: "Victoria",
    lastname: "CASTRE"
} as User;

export const mockCares = [
    {
        createAt: "2022-01-15 11:02:17",
        endDate: new Date().toString(),
        during: 30,
        title: "Bain bébé on va faire en sorte que ce titre soit long",
        description: "dede",
        createBy: "JULME",
        createFor: "VICTORIA",
        status: "completed"
    },
    {
        createAt: "2024-01-14 23:00:00",
        endDate: new Date().toString(),
        during: 120,
        title: "1Sommeil bébé",
        description: "cette description doit être longue aussi pour le test. Sinon, ça doit faire presque toute la largeur de la table.",
        createBy: "JULME",
        createFor: "VICTORIA",
        status: "En cours"
    },
    {
        createAt: "2022-01-15 11:02:17",
        endDate: new Date().toString(),
        during: 30,
        title: "2Bain bébé",
        description: "dede",
        createBy: "Julien",
        createFor: "VICTORIA",
        status: "completed"
    },
    {
        createAt: "2024-01-14 23:00:00",
        endDate: new Date().toString(),
        during: 120,
        title: "3Sommeil bébé",
        description: "dede",
        createBy: "Samano",
        createFor: "VICTORIA",
        status: "En cours",
    },
    {
        createAt: "2022-01-15 11:02:17",
        endDate: new Date().toString(),
        during: 30,
        title: "4Bain bébé",
        description: "dede",
        createBy: "JULME",
        createFor: "GLORIA",
        status: "completed"
    },
    {
        createAt: "2024-01-14 23:00:00",
        endDate: new Date().toString(),
        during: 120,
        title: "5Sommeil bébé",
        description: "dede",
        createBy: "DEMOS THERNE",
        createFor: "VICTORIA", isSelected: false,
        status: "En cours"
    },
    {
        createAt: "2022-01-15 11:02:17",
        endDate: new Date().toString(),
        during: 30,
        title: "6Bain bébé",
        description: "dede",
        createBy: "JULME",
        createFor: "VICTORIA",
        status: "completed"
    },
    {
        createAt: "2024-01-14 23:00:00",
        endDate: new Date().toString(),
        during: 120,
        title: "7Sommeil bébé",
        description: "dede",
        createBy: "JULME",
        createFor: "VICTORIA",

        status: "En cours"
    },
    {
        createAt: "2022-01-15 11:02:17",
        endDate: new Date().toString(),
        during: 30,
        title: "8Bain bébé",
        description: "dede",
        createBy: "Julien",
        createFor: "VICTORIA",
        status: "completed"
    },
    {
        createAt: "2024-01-14 23:00:00",
        endDate: new Date().toString(),
        during: 120,
        title: "9Sommeil bébé",
        description: "dede",
        createBy: "Samano",
        createFor: "VICTORIA",
        status: "En cours"
    },
    {
        createAt: "2022-01-15 11:02:17",
        endDate: new Date().toString(),
        during: 30,
        title: "Bain bébé",
        description: "dedes",
        createBy: "JULME",
        createFor: "GLORIA",
        status: "completed"
    },
    {
        createAt: "2024-01-14 23:00:00",
        endDate: new Date().toString(),
        during: 120,
        title: "Sommeil bébé",
        description: "dede",
        createBy: "DEMOS THERNE",
        createFor: "VICTORIA", isSelected: false,
        status: "En cours"
    }
];

