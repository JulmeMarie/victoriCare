import { IContactForm } from "../components/Forms/ContactForm/ContactForm";
import { defaultResult, errorResult, successResult } from "../utils/global-default-values";
import { CONTENTTYPES } from "../utils/Constants";
import { Util } from "../utils/Util";
import HttpService from "./HttpService";

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
            Util.checkMail(values.email) &&
            Util.checkName(values.username) &&
            Util.checkSubject(values.subject) &&
            Util.checkMessage(values.message)
        );
    }
}