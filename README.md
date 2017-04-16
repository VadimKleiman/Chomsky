# Chomsky

Запуск тестов

```
mvn test
```

Сборка и запуск

```
mvn package
java -cp target/CYK-1.0-SNAPSHOT.jar ru.spbau.mit.Main file_name
```
Визуализация дерева:

```
dot -Tpdf -Gdpi=350 -o test.pdf test.dot 
```
