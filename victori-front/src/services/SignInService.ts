import { ISignInForm } from "../components/Forms/SignInForm/SignInForm";
import { defaultResult, errorResult, successResult } from "../utils/global-default-values";
import { CONTENTTYPES } from "../utils/Constants";
import { Util } from "../utils/Util";
import HttpService from "./HttpService";

export class SignInService {
    signIn = async (formValues: ISignInForm) => {
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

    validate(values: ISignInForm): void {
        values.disable = !(
            Util.checkMail(values.email) &&
            Util.checkPassword(values.password1) &&
            Util.checkPassword(values.password2) &&
            values.password1 === values.password2
        );
    }
}