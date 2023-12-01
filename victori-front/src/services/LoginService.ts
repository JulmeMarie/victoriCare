import { ILogInForm } from "../components/Forms/LoginForm/LoginForm";
import { defaultResult } from "../utils/global-default-values";
import { CONTENTTYPES } from "../utils/Constants";
import { Util } from "../utils/Util";
import HttpService from "./HttpService";

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
        values.disable = !(Util.checkMail(values.email) && Util.checkPassword(values.password));
    }
}