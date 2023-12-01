import { ILogInForm } from "../components/Forms/LoginForm/LoginForm";
import { IRecoveryForm } from "../components/Forms/RecoveryForm/RecoveryForm";
import { defaultResult, successResult } from "../utils/global-default-values";
import { CONTENTTYPES } from "../utils/Constants";
import { Util } from "../utils/Util";
import HttpService from "./HttpService";

export class RecoveryService {
    recover = async (formValues: IRecoveryForm) => {
        return successResult;
        let result = { ...defaultResult };
        this.validate(formValues);
        if (formValues.disable) {
            result.isError = true;
            result.error = "Email renseigné est incorect";
            return result;
        }
        else {
            return HttpService.create(JSON.stringify(formValues), '/recover', CONTENTTYPES.JSON, true);
        }
    }

    validate(values: IRecoveryForm): void {
        values.disable = !Util.checkMail(values.email);
    }
}