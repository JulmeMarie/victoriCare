
import { IPasswordForm } from "../components/Forms/PasswordForm/PasswordForm";
import { defaultResult, errorResult, successResult } from "../utils/global-default-values";
import { CONTENTTYPES } from "../utils/Constants";
import { Util } from "../utils/Util";
import HttpService from "./HttpService";

export class PasswordService {
    reset = async (formValues: IPasswordForm) => {
        //return errorResult;
        return successResult;
        let result = { ...defaultResult };
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

    validate(values: IPasswordForm): void {
        values.disable = !(
            Util.checkPassword(values.password1) &&
            Util.checkPassword(values.password2) &&
            values.password1 === values.password2
        );
    }
}