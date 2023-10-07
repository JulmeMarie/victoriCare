export const Util =  class Util {

     static getJSON(url, callback) {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', url, true);
        xhr.responseType = 'json';
        xhr.onload = function() {
          var status = xhr.status;
          console.log(url);
          if (status === 200) {
            callback(null, xhr.response);
          } else {
            callback(status, xhr.response);
          }
        };
        xhr.send();
    };

    static toDate(dateString) {
      //const dateArray = dateString.split('-');
      //return Date.parse(dateArray[2] + '-' + dateArray[1] + '-' + dateArray[0]);
      return Date.parse(dateString);
    }

    static validNom(nom) {
      if(!nom) return false;
      var re = /[a-zA-Z0-9áàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ_ \-\s-]{2,20}/;
      return re.test(nom);
    }

    static validIdentifiant(identifiant) {
        return Util.validEmail(identifiant) || Util.validPseudo(identifiant);
    }

    static validEmail(email) {
      const regex = /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/;
        return regex.test(email);
    }

    static validPseudo(pseudo) {
      const regex = /^[a-zA-ZáàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ_-]{2,6}[0-9]{2,3}$/;
      return regex.test(pseudo);
    }

    static validMotdepasse(password) {
      const regex = /[a-zA-ZáàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ_\-0-9]{5,10}/;
      console.log("valid password" + regex.test(password));
      if(!regex.test(password)) return false;
		  return password.replace(/[a-zA-ZáàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ_-]/ig,'').length>=2;
    }

    static isBoolean(value) {
      return typeof value === 'boolean';
    }

    static urlToString(url, paramsArr) {
        const target = new URL(url);
        const params = new URLSearchParams();
        this.paramsArr.forEach((key, value) =>{ params.set(key, value); });
        target.search = params.toString();
        return target;
    }
}

export const ACTION = {
  BANNER : 'banner',
  CLOSECURRENT : 'close',
  ABOUT : 'about',
  GUIDE : 'guide',
  TERMS : 'terms',
  CAREERS : 'careers',
  EVENTGUEST : 'eventGuest',
  EVENTLIST : 'eventList',
  CONTACTLIST : 'contactList',
  EVENTFORM : 'eventForm',
  EVENTCODE: 'eventCode',
  CONTACTFORM : 'contactForm',
  LOGINFORM : 'loginForm',
  SIGNINFORM : 'signinForm',
  ACCOUNTFORM : 'accountForm',
  EVENT:'evenement'
};
