export default class TaskService {
    list() {
       return [
        {id:1, name:'Faire pipi',procedure:[]}, 
        {id:2, name:'Faire caca',procedure:[]}, 
        {id:3,name:'avoir faim',procedure:[]}, 
        {id:4, name:'s\'endormir',procedure:[]}, 
        {id:5, name:'se réveiller', procedure:['vérifier sa couche','si elle a faim, donne lui à manger (sein ou biberon)', 'La laisser jouer dans son berceau ou dans la balancelle', 'ne pas la laisser sans surveillance', 'etc.']},
        {id:6, name:'prendre son bain',procedure:[]},
        {id:7, name:'Pleurer',procedure:[]}
     ];
    };
    getById(id) {
        let element = this.list().filter((task) => {
            return task.id === parseInt(id);
        });
        return element[0];
    }
}