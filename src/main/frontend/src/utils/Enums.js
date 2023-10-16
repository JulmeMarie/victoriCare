export const EContentTypes = Object.freeze({
    Activity : "ACTIVITY",
    Project : "PROJECT",
    Post : "POST",
    News : "NEWS",
    Partner : "PARTNER",
    Organization : "ORGANIZATION",
    Relais : "RELAIS",
    Dental : "DENTAL",
    Donation : "DONATION",
    Mention : "MENTION"
});

export const EFMCValuesTypes = Object.freeze({
    Label : "LABEL",
    Coordinate : "COORDINATE",
    Site : "SITE",
    Email : "E-MAIL",
    Phone : "PHONE",
});



export const EUserTypes = Object.freeze({
    MEMBER : "MEMBER",
    TEAM : "TEAM",
});

export const EAlertTypes = Object.freeze({
    SUCCESS : {
        key : "success",
        value : "Succès"
    },
    WARNING : {
        key : "warning",
        value : "Attention!"
    },
    INFO : {
        key : "info",
        value : "Informations"
    },
    ERROR : {
        key : "danger",
        value : "Erreur"
    },
});

export const EDialogTypes = Object.freeze({
    INFO : {
        key : "info",
        value : "Informations"
    },
    ALERT : {
        key : "alert",
        value : "Alerte"
    },
    CONFIRM : {
        key : "confirm",
        value : "Confirmation"
    },
});

export const ERoleTypes = Object.freeze({
    MEMBER : {key : "MEMBER", value : "Membre"},
    PRESIDENT : { key : "PRESIDENT", value : "Président(e)"},
    TRESURER : { key :"TRESURER", value : "Trésorieer(ère)"},
    SECRETARY : {key : "SECRETARY", value : "Secrétaire"}
});

export const ERightTypes = Object.freeze({
    USER : {key : "USER", value : "Simple Utilisateur(trice)"},
    MODERATOR : {key : "MODERATOR", value : "Modérateur(trice)"},
    ADMINISTRATOR : {key : "ADMINISTRATOR", value : "Administrateur(trice)"},
});

export const ImagePosition = Object.freeze({
    LEFT : "left",
    RIGHT : "right",
});

export const MenuType = Object.freeze({
    Horizontal : "horizontal",
    Vertical : "vertical",
    HomeNav : "horizontal",
    DashboardNav : "dashboard-horizontal",
    Sidebar : "dashboard-vertical",
});

export const ActionTypes = Object.freeze({
    UPDATE_EMAIL : "update_email",
    UPDATE_PASSWORD : "update_password",
    RECOVERY : "recovery",
    ACCOUNT : "account",
})