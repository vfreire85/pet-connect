# pet-connect
Projeto Integrador - Análise e Desenvolvimento de Sistemas - Faculdade Senac EAD - 2025.01 - Grupo 07

Grupo composto por:
VIVIANE DE FREITAS CIPOLLARI - Desenvolvimento de personas e jornada de usuário, modelo de dados e protótipos;
NELSON JOSÉ COLAVITE JUNIOR - Landing page
JOÃO PEDRO DOS SANTOS - Banco de dados e servidor backend PHP
ÉDINA GRAZIELA HILLESHEIN - Banco de dados e servidor backend PHP
LUCAS DO CARMO FREITAS SANTOS - Frontend e integração
GUSTAVO HORIKOCHI - Frontend e integração
VICTOR FREIRE DE CARVALHO - Frontend e integração

Localização da landing page no Github pages: https://vfreire85.github.io/pet-connect-lp/

Vídeo anexo "trabalho pet-connect completo.mp4"

OBSERVAÇÕES:

- O frontend está armazenado no arquivo pet-connect-app.tar.gz. Ele foi feito na linguagem de programação Kotlin e IDE Android Studio. Como foi reaproveitado de um projeto anterior de outros membros do grupo, ele pode conter ainda referências ao projeto antigo, embora reconfigurado para funcionar como novo;

- O frontend está configurado para funcionar na máquina de desenvolvimento dos membros do grupo, portanto com o endereço IP apontando para o respectivo servidor Apache. Ao rodar o projeto, o tutor deverá fazer o deploy dos arquivos contidos na pasta "api" e configurar o servidor para a linguagem PHP, e também um banco de dados PostgreSQL de acordo com o arquivo .sql incluso. Depois disso, deverão ser alterados nas activities do Kotlin a URL do servidor, apontando para o endereço IP da máquina onde o servidor estará hospedado;

- As funções de geolocalização infelizmente não puderam ser implementadas a tempo, portanto na HomeActivity está configurado apenas um placeholder estático. No entanto, o frontend é capaz de fazer requisições REST ao servidor, estando habilitado o login de usuários comuns e administrativos (admin@email.com, senha '123456'), bem como a exibição do nome dos usuários, alteração de senhas e deleção (apagamento) de usuários no banco de dados através do próprio frontend.

São Paulo, 1º Semestre de 2025.
