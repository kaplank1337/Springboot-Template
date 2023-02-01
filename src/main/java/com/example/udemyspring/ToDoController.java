package com.example.udemyspring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ToDoController {

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping("/getAllTodo")
    public ResponseEntity<Iterable<Todo>> getAllTodo(){
        Iterable<Todo> allTodos = todoRepository.findAll();
        return new ResponseEntity(allTodos,HttpStatus.OK);
    }

    @GetMapping("/todo")
    public ResponseEntity<Todo> get(@RequestParam(value ="id")Long id){

            //get todo from db by id
           Optional<Todo> todoInDb = todoRepository.findById(id);
           if(todoInDb.isPresent()){
               return new ResponseEntity<Todo>(todoInDb.get(), HttpStatus.OK);
           }
            return new ResponseEntity("No todo found with id " + id, HttpStatus.NOT_FOUND);

    }
    @DeleteMapping("/delete")
    public ResponseEntity<Todo> delete(@RequestParam (value="id")Long id){

        Optional<Todo> todoInDB = todoRepository.findById(id);

        if(todoInDB.isPresent()){
            todoRepository.deleteById(id);
            return new ResponseEntity(id + " is succcessful deleted",HttpStatus.OK);
        }
        return new ResponseEntity("No Entry found!",HttpStatus.NOT_FOUND);

    }

    @PutMapping("/change")
    public ResponseEntity<Todo> change(@RequestBody Todo changeTodo){
        Optional<Todo> todoInDB = todoRepository.findById(changeTodo.getId());

        if(todoInDB.isPresent()){
            return new ResponseEntity<Todo>(todoRepository.save(changeTodo), HttpStatus.OK);
        }
        return new ResponseEntity("No Entry found!",HttpStatus.NOT_FOUND);
    }

    @PostMapping("/todo")
    public ResponseEntity<Todo> create(@RequestBody Todo newTodo){
        todoRepository.save(newTodo);
        return new ResponseEntity<Todo>(newTodo,HttpStatus.OK);
    }

    @PatchMapping("/change")
    public ResponseEntity<Todo> changeSomething(@RequestParam(value="isDone") boolean isDone,
                                                @RequestParam(value="id") Long changeTodo){
        Optional<Todo> todo = todoRepository.findById(changeTodo);
        if(todo.isPresent()){
            todo.get().setIsDone(isDone);
            todoRepository.save(todo.get());
            return new ResponseEntity<Todo>(todo.get(),HttpStatus.OK);
        }
        return new ResponseEntity("Nothing there BITCH",HttpStatus.NOT_FOUND);
    }
}
