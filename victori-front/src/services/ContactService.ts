import { IContactForm } from "../components/Forms/ContactForm/ContactForm";
import { defaultResult, errorResult, successResult } from "../utils/global-default-values";
import { CONTENTTYPES } from "../utils/Constants";
import HttpService from "./HttpService";
import { checkMail, checkMessage, checkName, checkSubject } from "../utils/global-util";

export class ContactService {
    contact = async (formValues: IContactForm) => {
        //return errorResult;
        let result = { ...defaultResult };
        return successResult;
        this.validate(formValues);
        if (formValues.disable) {
            result.isError = true;
            result.error = "Un ou plusieurs champs mal renseignés";
            return result;
        }
        else {
            return HttpService.create(JSON.stringify(formValues), '/signin', CONTENTTYPES.JSON, true);
        }
    }
    validate(values: IContactForm): void {
        values.disable = !(
            checkMail(values.email) &&
            checkName(values.username) &&
            checkSubject(values.subject) &&
            checkMessage(values.message)
        );
    }
}