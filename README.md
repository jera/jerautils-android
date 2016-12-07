# JeraUtils - Android
JeraUtils para Android é uma coleção de pequenos utilitários criados com o intuito de reduzir
boilerplate no nosso desenvolvimento diário de aplicativos, tails como placeholders para erros,
carregamento de telas, alertas, preferences e paginação.

## How to Install
Add this repository (Temporary) to your root build.gradle
```groovy
repositories {
        maven {
            url "http://dl.bintray.com/daividssilverio/jerautils-android"
        }
    }
```

Add this to your dependencies
```groovy
compile 'br.com.jera.jerautils-android:jerautils-android:0.1.0'
```

## Dependências
```groovy
compile "com.android.support:design:x.y.z"
```

## Adicionando credenciais de deploy

Antes de poder fazer o deploy da lib, é necessário adicionar as credenciais de um usuário do bintray
pertencente à organização jera.
https://bintray.com/jera

Abra (ou crie) um arquivo chamado local.properties no diretório root do projeto.
Adicione as seguintes linhas:

```
bintray.user=BINTRAY_USERNAME
bintray.apikey=BINTRAY_API_KEY
```

Subtitua o BINTRAY_USERNAME e o BINTRAY_API_KEY pelo seu nome de usuário e api key do bintray.
A api key pode ser encontrada em:
https://bintray.com/profile/edit

## Deploy do projeto

O deploy para o bintray é feito usando o plugin oficial para gradle:
https://github.com/bintray/gradle-bintray-plugin

Para gerar uma nova versão:

1. Abra o arquivo build.gradle e troque o valor do **project.version** para a versão desejada.
2. Abra o terminal na pasta do projeto e insira:

```bash
$ ./gradlew jerautils:clean jerautils:assembleRelease bintrayUpload
```