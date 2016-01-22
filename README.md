# Instruções de Uso

Antes de rodar o projeto, é necessário associar uma chave de API do Google Maps ao projeto.

Dentro do arquivo: *app/src/main/res/values/google_maps_api.xml* é necessário editar o código para incluir a sua chave da API do google maps:

```
<resources>
    <string name="google_maps_key" templateMergeStrategy="preserve" translatable="false">YOUR_API_KEY_HERE</string>
</resources>
```