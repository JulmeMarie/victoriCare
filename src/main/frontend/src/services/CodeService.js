import {ActionTypes, EAlertTypes} from "../Utils/Enums";
import {HttpService} from "./HttpService";
const VALIDATE_ENDPOINT_CODE = "right-anm/validation";
const RESPONSE_DURATION = 1000;

const regexP = /^[0-9]{6}$/;

export class CodeService {
  data = {};

  static newInstance() {
    return new CodeService();
  }

  initForm = (type, code, id, actionType) => {
    const formValues = {code, id, type : type || actionType}
    if (this.isFormValid(formValues)) {
      this.data.setFormValues(formValues);
      setTimeout(() => {
        this.submitForm(null, formValues);
      }, RESPONSE_DURATION);
    }
    else {
      this.data.setFormValues({code, id, type : type || actionType});
    }
  };

  textChangeHandler = (e, formValues) => {
    this.data.setFormValues({...formValues, [e.target.name]: e.target.value});
  };

  isFormValid = formValues => {
    return this.isFormFullfilled(formValues) 
    && formValues.id > 0
    && [ActionTypes.RECOVERY, ActionTypes.ACCOUNT, ActionTypes.UPDATE_PASSWORD].includes(formValues.type)
    && regexP.test(formValues.code);
  };

  isFormFullfilled = formValues => {
     return formValues.code && formValues.id && formValues.type;
  };

  submitForm = (e, formValues) => {
    if (e) e.preventDefault();
    this.data.setLoading(true);
    this.data.setResult(null);

    if (!this.isFormValid(formValues)) {
      this.data.setLoading(false);
      this.data.setResult({
        type: EAlertTypes.WARNING,
        message:
          "Le code de validation n'a pas été renseigné correctement. Prière de corriger et réessayer. Merci",
      });
      return;
    }
    this.validate(formValues);
  };

  validate = async (formValues) => {
    return HttpService.update(
      JSON.stringify(formValues),
      VALIDATE_ENDPOINT_CODE,
      "JSON",
      true
    ).then(respone => {
      this.data.setLoading(false);
      if (respone.ok) {
        this.data.setResult({
          type: EAlertTypes.SUCCESS,
          message: "La validation a été effectuée.",
        });
        setTimeout(() => {
          if ([ActionTypes.RECOVERY, ActionTypes.ACCOUNT].includes(formValues.type)) {
            this.data.navigate("/password/" + formValues.code + "/" + formValues.id);
          } 
          else if(typeof this.data.showCodeForm === "function"){
            this.data.showCodeForm(false);
          }
        }, RESPONSE_DURATION);
      } else {
        this.data.setResult({
          type: EAlertTypes.ERROR,
          message: "La validation a échoué. Prière de réessayer. Merci.",
        });
      }
    });
  };
}
