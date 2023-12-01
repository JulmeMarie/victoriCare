import { Navigate } from "react-router-dom";
/**
 * This component acts like a guard to protect rootes
 * @param {*} props 
 * @returns 
 */
const RequireAuth = (props) => {
    return props.token ? props.children : <Navigate to="/signin" />;
}

export default RequireAuth;


