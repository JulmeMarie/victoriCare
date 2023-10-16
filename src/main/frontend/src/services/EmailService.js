import {HttpService} from "./HttpService";
import {EAlertTypes} from "../Utils/Enums";
import {setOnlineUser} from "../redux/reducer";
import {ActionTypes} from "../Utils/Enums";

const RECOVERY_ENDPOINT_EMAIL = "right-anm/recover";
const UPDATE_EMAIL_ENDPOINT_EMAIL = "right-usr/update/email";
const RESPONSE_DURATION = 1000;
const regexP =
  /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;

export class EmailService {
  data = {};

  static newInstance() {
    return new EmailService();
  }

  initForm = (type, formValues) => {
    if (this.isFormFullfilled(formValues)) {
      if (!this.isEmailCheck(formValues)) {
        this.data.setResult({
          type: EAlertTypes.WARNING,
          message:
            "Les valeurs des deux champs e-mail doivent être identiques. Merci de corriger.",
        });
      } else if (
        !this.isNotSameAsActual(formValues) &&
        type === ActionTypes.UPDATE_EMAIL
      ) {
        this.data.setResult({
          type: EAlertTypes.WARNING,
          message:
            "La nouvelle adresse E-mail ne doit pas à être identique à l'ancienne. Merci de corriger.",
        });
      } else {
        this.data.setResult(null);
      }
    }
  };

  textChangeHandler = (e, formValues) => {
    this.data.setFormValues({...formValues, [e.target.name]: e.target.value});
  };

  isFormValid = formValues => {
    return (
      this.isFormFullfilled(formValues) &&
      this.isEmailCheck(formValues) &&
      this.isNotSameAsActual(formValues) &&
      regexP.test(formValues.email) &&
      regexP.test(formValues.emailCheck)
    );
  };

  isFormFullfilled = formValues => {
    return formValues.email !== null && formValues.emailCheck !== null;
  };

  isEmailCheck = formValues => {
    return formValues.email === formValues.emailCheck;
  };

  isNotSameAsActual = formValues => {
    if (this.data.type === ActionTypes.RECOVERY) return true;
    return (
      this.data.user.email !== formValues.email &&
      this.data.user.email !== formValues.emailCheck
    );
  };

  recover = async email => {
    this.data.setResult(null);
    HttpService.update(email, RECOVERY_ENDPOINT_EMAIL, "JSON", true).then(
      response => {
        this.data.setLoading(false);
        if (response.ok) {
          this.data.setResult({
            type: EAlertTypes.SUCCESS,
            message:
              "Un e-mail contenant le code de validation de l'opération vient de vous être envoyé. Renseignez-le dans le formulaire suivant. Merci.",
          });
          //dispatch(setOnlineUser(response.data));
          setTimeout(() => {
            this.data.navigate(
              "/code/0/" + ActionTypes.RECOVERY + "/" + response.data.id
            );
          }, RESPONSE_DURATION);
        } else {
          this.data.setResult({
            type: EAlertTypes.ERROR,
            message: "Echec de la récupération de compte. Merci de réessayer",
          });
        }
      }
    );
  };

  updateEmail = async email => {
    this.data.setResult(null);
    HttpService.update(email, UPDATE_EMAIL_ENDPOINT_EMAIL, "JSON", true).then(
      response => {
        this.data.setLoading(false);
        if (response.ok) {
          this.data.setResult({
            type: EAlertTypes.SUCCESS,
            message: "Mise à jour d'adresse e-mil éffectué.",
          });
          this.data.dispatch(setOnlineUser(response.data));
          setTimeout(() => {
            this.data.showCodeForm(true);
          }, RESPONSE_DURATION);
        } else {
          this.data.setResult({
            type: EAlertTypes.ERROR,
            message: "Echec de la mise de l'adresse e-mail. Merci de réessayer",
          });
        }
      }
    );
  };

  submitForm = (e, formValues) => {
    e.preventDefault();
    this.data.setLoading(true);
    this.data.setResult(null);
    if (!this.isFormValid(formValues)) {
      this.data.setLoading(false);
      this.data.setResult({
        type: EAlertTypes.WARNING,
        message:
          "Certains champs du formulaire n'ont pas été renseignés correctement. Prière de corriger et réessayer. Merci",
      });
      return;
    }
    if (this.data.type === ActionTypes.UPDATE_EMAIL) {
      this.updateEmail(formValues.email);
    } else if (this.data.type === ActionTypes.RECOVERY) {
      this.recover(formValues.email);
    }
  };
}
