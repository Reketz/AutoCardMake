# AutoCardMake

O AutoCardMake é um projeto que gera corte de trechos de vídeos baseado em parâmetros pré-estabelecidos pelo usuário e cria um arquivo de importação no [Anki](https://apps.ankiweb.net).

O projeto permite que o usuário selecione o vídeo e outros parâmetros e o algoritmo faz o resto definindo assim o tempo de início e fim do trecho a ser cortado, duração do trecho e criação do arquivo `.txt` de importação.

Uma vez que o usuário tenha definido os parâmetros, o algoritmo irá realizar a edição de forma automatizada, cortando o trecho desejado e salvando-o em uma pasta específica também definida pelo usuário.

## Installation

- Clone o repositório

  ```sh
  git clone https://github.com/Reketz/AutoCardMake.git
  ```
  
## Parameters

Objeto `CardMake` e seus parâmetros:

- `{separator}` - Separador entre o tempo de início e fim da cena que fica dentro do arquivo de legendas.
- `{pathVideoTarget}` - Caminho do vídeo para ser gerado os cortes.
- `{pathOutput}` - Caminho onde será gerado o arquivo de importação para o [Anki](https://apps.ankiweb.net).
- `{linesFrom}` - Arquivo de legenda do vídeo convertida em um `List<String>`.
- `{linesTo}` - Arquivo de legenda do vídeo traduzidas convertida em um `List<String>`.
- `{cutVideo}` - O objeto `CutVideoImpl` ou alguma implementação do CutVideo.

Na classe `GeneratorCardMake` tem um método estático `toGenerate` e seus parâmetros são:

- `{CardMake}` - Objeto CardMake.
- `{sceneNumber}` - Número da cena que inicia os cortes (dentro do arquivo de legenda).
- `{generateTo}` - Número da cenas para serem geradas.

## Examples

```java
  public static void main(String[] args) throws IOException {
        String resourcesPath = "C:\\temp\\generator\\03\\";

        String subtitleUS = "C:\\temp\\generator\\03\\S01E03US.srt";
        String subtitleBr = "C:\\temp\\generator\\03\\S01E03PT.srt";

        List<String> linesFrom = getFileAsList(subtitleUS, null);
        List<String> linesTo = getFileAsList(subtitleBr, null);

        CutVideo cutVideo = new CutVideoImpl();

        String pathVideo = resourcesPath + "S1E3.mkv";
        String pathOutput = "C:\\temp\\generator\\03\\output";
        String separator_time =  " --> ";

        CardMake cardMake = new CardMake(separator_time, pathVideo, pathOutput, linesFrom, linesTo, cutVideo);
        GeneratorCardMake.toGenerate(cardMake, 1, 353);
    }
```

## File Import

Depois de gerar o arquivo `.txt` e gerar os cortes é preciso fazer alguns procedimentos antes de importar o arquivo.

Primeiro você deve mover os cortes gerados para dentro da pasta `collection.media` que fica dentro da pasta do seu perfil no [Anki](https://apps.ankiweb.net).

![](https://github.com/Reketz/AutoCardMake/blob/main/src/main/resources/copy_cuts_videos.gif)

Depois é só fazer a importação do arquivo dentro do [Anki](https://apps.ankiweb.net) note que o Card tem 3 campos, são eles:

- `{Video}` - Nome do video que foi adicionado na pasta `collection.media`.
- `{Front}` - Texto gerado pela cena (dentro do arquivo de legenda).
- `{Back}` - Texto gerado pela cena da legenda traduzida.

![](https://github.com/Reketz/AutoCardMake/blob/main/src/main/resources/file_import.gif)
