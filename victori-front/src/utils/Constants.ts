export const LANGUAGES = {
    EN: "en",
    ES: "es",
    FR: "fr",
    HT: "ht"
} as const;

export const ALERTS = {
    SUCCESS: "success",
    WARNING: "warning",
    INFO: "info",
    ERROR: "error"
} as const;

export const DIALOGS = {
    INFO: "info",
    ALERT: "Alerte",
    CONFIRM: "confirm",
} as const;

export const ROLES = {
    PARENT: "parent",
    BABYSITTER: "baby-sitter",
    CHILD: "child"
} as const;

export const RIGHTS = {
    USER: "user",
    MODERATOR: "moderator",
    ADMINISTRATOR: "administrator"
};


export const ACTIONS = {
    UPDATE_EMAIL: "update_email",
    UPDATE_PASSWORD: "update_password",
    RECOVERY: "recovery",
    ACCOUNT: "account",
} as const;

export const DIRECTIONS = {
    LEFT: "left",
    RIGHT: "right",
} as const

export const FORMNAMES = {
    LOGIN_OWNER: 'login-owner-form',
    LOGIN_ACCESS: 'login-access-form',
    RECOVERY: "login-recovery-form",
    SIGNIN: "signin-form",
    CODE: "code-form",
    PASSWORD: "password-form",
    CONTACT: "contact-form"
} as const;

export const CONTENTTYPES = {
    JSON: "application/json"
} as const;

export const DEVICES = {
    SMALL: 480,
    MEDIUM: 768,
    LARGE: 1024,
    DEFAULT: 1280
} as const;