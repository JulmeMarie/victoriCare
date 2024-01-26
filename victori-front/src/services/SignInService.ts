import { ISignInForm } from "../components/Forms/SignInForm/SignInForm";
import { defaultResult, errorResult, successResult } from "../utils/global-default-values";
import { CONTENTTYPES } from "../utils/Constants";
import HttpService from "./HttpService";
import { checkMail, checkPassword } from "../utils/global-util";

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
            checkMail(values.email) &&
            checkPassword(values.password1) &&
            checkPassword(values.password2) &&
            values.password1 === values.password2
        );
    }
}