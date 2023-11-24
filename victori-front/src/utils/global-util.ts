export const toDate = (dateString: string) => {
  //const dateArray = dateString.split('-');
  //return Date.parse(dateArray[2] + '-' + dateArray[1] + '-' + dateArray[0]);
  return Date.parse(dateString);
}

export const checkCode = (code: number) => {
  if (!code) return false;
  return code >= 100000 && code <= 999999;
}

export const checkName = (nom: string) => {
  if (!nom) return false;
  var re = /[a-zA-Z0-9áàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ_ \-\s-]{2,20}/;
  return re.test(nom);
}

export const checkSubject = (subject: string) => {
  if (!subject) return false;
  var re = /[a-zA-Z0-9áàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ_ \-\s-]{2,50}/;
  return re.test(subject);
}

export const checkMessage = (message: string) => {
  if (!message) return false;
  var re = /[a-zA-Z0-9áàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ_ \-\s-]{10,500}/;
  return re.test(message);
}

export const checkID = (identifiant: string) => {
  return checkMail(identifiant) || checkPseudo(identifiant);
}

export const checkMail = (email: string) => {
  const regex = /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/;
  return regex.test(email);
}

export const checkPseudo = (pseudo: string) => {
  const regex = /^[a-zA-ZáàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ_-]{2,6}[0-9]{2,3}$/;
  return regex.test(pseudo);
}

export const checkPassword = (password: string) => {
  //const passwordRegex = /(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[a-zA-Z0-9]{6,}/;
  const regex = /[a-zA-ZáàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ_\-0-9]{5,10}/;
  if (!regex.test(password)) return false;
  return password.replace(/[a-zA-ZáàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ_-]/ig, '').length >= 2;
}

export const isBoolean = (value: string) => {
  return typeof value === 'boolean';
}

export const urlToString = (url: string, paramsArr: any) => {
  const target = new URL(url);
  const params = new URLSearchParams();
  paramsArr.forEach((key: string, value: string) => { params.set(key, value); });
  target.search = params.toString();
  return target;
}

/**
 * Check any type of arrays, and return true if it is null, undefined or empty.
 * In any other case, this function return false.
 * @param array 
 * @returns boolean
 */
export const isNullOrEmpty = (array: Array<Object> | null | undefined) => {
  return !array || array.length === 0;
}

/**
 * Check any type of arrays and return true if the array contains 1 element.
 * In any other case (null, undefined, more than one element), this function return false.
 * @param array 
 * @returns boolean
 */
export const isSingleton = (array: Array<Object> | null | undefined) => {
  return array && array.length === 1;
}

/**
 * Check any type of arrays and return true if the array contains 2 elements.
 * In any other case (null, undefined, less or more than 2 elements), this function return false.
 * @param array 
 * @returns boolean
 */
export const isPair = (array: Array<Object> | null | undefined) => {
  return array && array.length === 2;
}

/**
 * Check any type of arrays and return true if the array contains more than 1 element.
 * In any other case (null, undefined, less than 2 elements), this function return false.
 * @param array 
 * @returns boolean
 */
export const isMultiple = (array: Array<Object> | null | undefined) => {
  return array && array.length > 1;
}

export const toString = (dateStr: string) => {
  if (!dateStr) return null;
  let date = new Date(dateStr);
  return date.toLocaleString('fr-FR');
}

export const isNullOrUndefined = (object: Object | null | undefined) => {
  return object === null || object === undefined;
}

export const isIncluded = (target: Array<Object> | undefined, container: Array<Object> | undefined) => {
  return (target || []).every(targetItem =>
    (container || []).some(containerItem => isEqual(targetItem, containerItem))
  );
}

export const isEqual = (object1: Object | null | undefined, object2: Object | null | undefined) => {
  return (isNullOrUndefined(object1) || isNullOrUndefined(object2)) ?
    false : JSON.stringify(object1) === JSON.stringify(object2)
}