import {ActionTypes} from "../Utils/Enums";
import {HttpService} from "./HttpService";
import {EAlertTypes} from "../Utils/Enums";
const UPDATE_PASSWORD_ENDPOINT_PASSWORD = "right-anm/update/password";
const RESPONSE_DURATION = 1000;

const passwordRegex = /(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[a-zA-Z0-9]{6,}/;
const codeRegex = /^[0-9]{6}$/;

export class PasswordService {
  data = {};

  static newInstance() {
    return new PasswordService();
  }

  initForm = formValues => {
    if (this.isFormFullfilled(formValues)) {
      if (!this.isPasswordValid(formValues)) {
        this.data.setResult({
          type: EAlertTypes.WARNING,
          message:
            "Le mot de passe doit avoir au moins 6 caractères dont une lettre minuscule, une lettre majuscule et  un chiffre au minimum. Prière de corriger et réessayer. Merci",
        });
      } else if (!this.isPasswordCheck(formValues)) {
        this.data.setResult({
          type: EAlertTypes.WARNING,
          message:
            "Le mot de passe de vérification doit être identique au nouveau. Merci de corriger.",
        });
      } else if (
        this.data.type === ActionTypes.UPDATE_PASSWORD &&
        !this.isNotSameAsActual(formValues)
      ) {
        this.data.setResult({
          type: EAlertTypes.WARNING,
          message:
            "Le nouveau mot de passe ne doit pas à être identique à l'ancien. Merci de corriger.",
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
    let check =
      this.isFormFullfilled(formValues) &&
      this.isPasswordCheck(formValues) &&
      this.isPasswordValid(formValues);
    return this.data.type === ActionTypes.UPDATE_PASSWORD
      ? check && this.isNotSameAsActual(formValues)
      : check;
  };

  isPasswordValid = formValues => {
    let check =
      passwordRegex.test(formValues.newPassword) &&
      passwordRegex.test(formValues.passwordCheck);
    return this.data.type === ActionTypes.UPDATE_PASSWORD
      ? check && passwordRegex.test(formValues.password)
      : check;
  };

  isFormFullfilled = formValues => {
    let check =
      formValues.newPassword !== null && formValues.passwordCheck !== null;
    return this.data.type === ActionTypes.UPDATE_PASSWORD
      ? check && formValues.password !== null
      : check;
  };

  isPasswordCheck = formValues => {
    return formValues.newPassword === formValues.passwordCheck;
  };

  isNotSameAsActual = formValues => {
    return (
      formValues.password !== formValues.newPassword &&
      formValues.password !== formValues.passwordCheck
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

    if (
      this.data.type === ActionTypes.RECOVERY &&
      (!codeRegex.test(this.data.code) || isNaN(this.data.id))
    ) {
      this.data.setLoading(false);
      this.data.setResult({
        type: EAlertTypes.ERROR,
        message:
          "Cette page contient des données incorrectes. Prière rafraïchir et réessayer. Merci",
      });
    } else {
      this.updatePassword(formValues.newPassword);
    }
  };

  updatePassword = async password => {
    return HttpService.update(
      JSON.stringify({
        id: this.data.id,
        code: this.data.code,
        type: this.data.type,
        password: password,
      }),
      UPDATE_PASSWORD_ENDPOINT_PASSWORD,
      "JSON",
      true
    ).then(response => {
      this.data.setLoading(false);
      if (response.ok) {
        this.data.setResult({
          type: EAlertTypes.SUCCESS,
          message: "Mise à jour de mot de passe effectué.",
        });
        setTimeout(() => {
          if (this.data.type === ActionTypes.UPDATE_PASSWORD) {
            this.data.showCodeForm(true);
          } else if (this.data.type === ActionTypes.RECOVERY) {
            document
              .getElementById("login")
              .scrollIntoView({behavior: "smooth"}, true);
          }
        }, RESPONSE_DURATION);
      } else {
        this.data.setResult({
          type: EAlertTypes.ERROR,
          message: "Mise à jour de mot de passe échoué. Prière de réessayer.",
        });
      }
    });
  };
}
