import { ILogInForm } from "../components/Forms/LoginForm/LoginForm";
import { defaultResult } from "../utils/global-default-values";
import { CONTENTTYPES } from "../utils/Constants";
import HttpService from "./HttpService";
import { checkMail, checkPassword } from "../utils/global-util";

export class LogInService {
    logIn = async (formValues: ILogInForm) => {
        let result = { ...defaultResult };
        this.validate(formValues);
        if (formValues.disable) {
            result.isError = true;
            result.error = "Email et/ou mot passe incorect";
            return result;
        }
        else {
            return HttpService.create(JSON.stringify(formValues), '/login', CONTENTTYPES.JSON, true);
        }
    }

    validate(values: ILogInForm): void {
        values.disable = !(checkMail(values.email) && checkPassword(values.password));
    }
}