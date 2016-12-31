========================
* Autor: César A. Magalhães
* Tecnologias: **Eclipse Neon**, **Spring MVC 4**, **Spring Security 4**, **Hibernate 5**
* Banco de dados: **PostgreSQL**
* Resumo: Aplicação CRUD (create, read, update, delete)
* Linguagem: **Java 8**
* Fonte: <https://github.com/cams7/casa_das_quentinhas>
* Site: <http://casa-das-quentinhas.herokuapp.com/>

O que é o sistema: Casa das Quentinhas?
-------------------
O sistema **Casa das Quentinhas** foi desenvolvido e testado no **Tomcat 8.5** e no **Jetty**. Esse e um sistema web que utiliza, principalmente, as tecnologias **Hibernate**, **Spring MVC** e **Spring Security**. O **Maven** é utilizado para automatizar a compilação de todos os códigos da aplicação.

Sistemas requeridos
-------------------
* [Microsoft Windows 10](https://www.microsoft.com/pt-br/software-download/windows10)
* [JDK 1.8](http://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html)
* [SQL Power Architect Data Modeling & Profiling Tool](http://www.sqlpower.ca/page/resources)
* [VirtualBox](https://www.virtualbox.org/)
* [Vagrant](https://www.vagrantup.com/)
* [Ubuntu 14.04.5 LTS](http://releases.ubuntu.com/14.04/)
* [Git](https://git-scm.com/downloads)
* [PostgreSQL](http://www.postgresql.org/download/)
* [Apache Tomcat](http://tomcat.apache.org/)
* [Jetty](http://www.eclipse.org/jetty/)
* [Eclipse IDE](https://eclipse.org/downloads/)
* [Maven](https://maven.apache.org/)
* [Spring](https://spring.io/)
* [Hibernate](http://hibernate.org/)
* [Heroku](https://www.heroku.com/)

Para rodar o programa
-------------------
* No **Windows**, instale o **VirtualBox**
* No **Windows**, instale o **Vagrant**
* No **Windows**, instale o **SQL Power Architect**
* No **Windows**, instale o **JDK 8**
* No **Windows**, faça o download e descompacte o **apache-maven-3.3.9**, em seguida, configure a *variável de ambiente* do *Maven* e reinicie o PC. 
* No **Windows**, para verificar se o *Maven* esta funcionando corretamente, execute no *Prompt de Comando*, a linha abaixo:
```	
mvn --version
```

* No **Windows**, faça o download e descompacte o **apache-tomcat-8.5.9**.
* No **Windows**, faça o download e descompacte o **eclipse-jee-neon-1a-win32-x86_64**. 
* No **Windows**, crie o diretório **java-dev** 
* No **Windows**, execute no *Prompt de Comando*, as linhas abaixo:
```	
cd java-dev
vagrant init ubuntu/trusty64
```

* Inclua as linhas abaixo no arquivo *Vagrantfile*
	
		config.vm.network "forwarded_port", guest: 8080, host: 8090
		config.vm.network "forwarded_port", guest: 5432, host: 5432
		
		config.vm.synced_folder "Diretório do Usuário/.m2", "/home/vagrant/.m2"
		config.vm.synced_folder "Diretório onde foi descompactado o Maven/apache-maven-3.3.9", "/home/vagrant/apache-maven"
		config.vm.synced_folder "Diretório onde foi descompactado o Tomcat/apache-tomcat-8.5.9", "/home/vagrant/apache-tomcat8"
		config.vm.synced_folder "Diretorio onde foi baixada a aplicação Casa da Quentinhas/casa_das_quentinhas", "/home/vagrant/github/casa_das_quentinhas"

* No **Windows**, dentro do diretório *java-dev*, execute no *Prompt de Comando*, a linha abaixo:
```
vagrant up --provider virtualbox
```

* Com o *Putty*, conecte via *SSH* na maquina virtual inicializada

* No **Ubuntu**, instale o **Git** através dos comandos abaixo:
```sh
sudo apt-get update
sudo apt-get install git-core
git config --global user.name <Nome do usuário>
git config --global user.email <E-mail do usuário>
git --version
```
		
* No **Ubuntu**, instale o **PostgreSQL** através dos comandos abaixo:
```sh
sudo apt-get install postgresql postgresql-contrib pgadmin3
```			

* No **Ubuntu**, instale o **Java 8** através dos comandos abaixo:
```sh
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update 
sudo apt-get install oracle-java8-installer
java -version
```
		
* No **Ubuntu**, instale o **Heroku** através dos comandos abaixo:
```sh
wget -O- https://toolbelt.heroku.com/install-ubuntu.sh | sh
heroku --version
```

* No **Ubuntu**, altere a senha do usuário *postgres* através dos comandos abaixo:
```sh
sudo -i -u postgres
psql postgres
ALTER USER postgres WITH PASSWORD 'postgres';
\q
exit
```		
		
* Para disponibilizar acesso remoto ao banco **PostgreSQL**, você terá que alterar os arquivos *postgresql.conf* e *pg_hba.conf* no diretório */etc/postgresql/9.3/main*; para isso, siga os passos abaixo:
```sh
sudo su - postgres
vim /etc/postgresql/9.3/main/postgresql.conf
```		
		
1. Digite */listen* para busca a linha *#listen_addresses ...*
2. Aperte a tecla *i* para editar o arquivo
3. Remova o caráter *#* do inicio da linha, e altere *'localhost'* por _'*'_
4. Após alteração, a linha ficará como segue abaixo:
	
		listen_addresses = '*' ...
	
5. Aperte a tecla *ESC*
6. Digite *:wq* para salvar a alteração do arquivo *postgresql.conf*
```sh
vim /etc/postgresql/9.3/main/pg_hba.conf
```		
		
1. Digite */127.0.0.1* para busca a linha *host all all ...*
2. Aperte a tecla *i* para editar o arquivo
3. Altere *127.0.0.1/32* por *0.0.0.0/0*
4. Após alteração, a linha ficará como segue abaixo:
	
		host all all 0.0.0.0/0 md5
	
5. Aperte a tecla *ESC*
6. Digite *:wq* para salvar a alteração do arquivo *pg_hba.conf*
```sh
exit
```		
		
* Feito a alteração, basta reiniciar o **PostgreSQL**
```sh
sudo /etc/init.d/postgresql restart
```
				
* Obs.: Para testar as alterações no **PostgreSQL**, execute o comando abaixo:
```sh
psql -U postgres -h <IP da maquina>
\q
```				
		
* No **Ubuntu**, configure as *variáveis de ambiente* através dos comandos abaixo:
```sh
echo 'export MVN_HOME=/home/vagrant/apache-maven' >> ~/.bashrc
echo 'export PATH=$PATH:$MVN_HOME/bin' >> ~/.bashrc

echo 'export CATALINA_HOME=/home/vagrant/apache-tomcat8' >> ~/.bashrc

echo 'export DATABASE_URL=postgres://dono_da_cozinha:quentinha@localhost:5432/casa_das_quentinhas' >> ~/.bashrc
```	

* No **Windows**, dentro do diretório *java-dev*, execute no *Prompt de Comando*, a linha abaixo:
```
vagrant reload
```

* Com o *Putty*, conecte via *SSH* na maquina virtual reinicializada

* No **Ubuntu**, com **PostgreSQL**, crie o banco *casa_das_quentinhas* através dos comandos abaixo:
```sh
sudo -i -u postgres
psql -d template1 -U postgres
CREATE USER dono_da_cozinha WITH PASSWORD 'quentinha';
CREATE DATABASE casa_das_quentinhas;
GRANT ALL PRIVILEGES ON DATABASE casa_das_quentinhas to dono_da_cozinha;
\q
exit

sudo adduser dono_da_cozinha
```		

* No **Ubuntu**, para baixar o projeto *casa_das_quentinhas*, execute as linhas abaixo:
```sh
cd /home/vagrant/github/
git clone https://github.com/cams7/casa_das_quentinhas.git
cd casa_das_quentinhas

git remote add origin <URL do repositório>
git remote -v
git pull origin master
git push origin master
```

* No **Ubuntu**, para criar as tabelas do banco, execute as linhas abaixo:
```sh
su - dono_da_cozinha
psql -d casa_das_quentinhas -U dono_da_cozinha
```

* No banco de dados *casa_das_quentinhas*, execute os comandos SQL dos arquivos **casa_das_quentinhas-psql-ddl.sql**, **uf-dml.sql**, **cidade-ddd31-dml.sql**, **usuario-dml.sql**, **empresa-dml.sql**, **funcionario-dml.sql** e **cliente-dml.sql** na ordem que foram informados. Esses arquivos estão localizados no diretório *database*.
* Em seguida, execute as linhas abaixo:
```sh
\q
exit
```	

* No **Ubuntu**, para testar a aplicação, execute as linhas abaixo:	
```sh
cd /home/vagrant/github/casa_das_quentinhas
mvn clean package -DskipTests
java $JAVA_OPTS -jar casa_das_quentinhas-web/target/dependency/jetty-runner.jar --host 0.0.0.0 casa_das_quentinhas-web/target/*.war
```

* No **Windows**, abra um navegador e informe o endereço **http://localhost:8090**
* Para logar na aplicação, a senha é **12345** e os e-mails de acesso são: **gerente@casa-das-quentinhas.com**, **atendente@casa-das-quentinhas.com**, **entregador@casa-das-quentinhas.com**, **empresa@casa-das-quentinhas.com** e **cliente@casa-das-quentinhas.com**.
	
* Caso deseje rodar o *casa_das_quentinhas* num [PAAS](https://pt.wikipedia.org/wiki/Plataforma_como_serviço), primeiro e necessário ter uma conta no **Heroku**. Após criar uma conta nesse site, execute as linhas abaixo:				
```sh		
heroku login
heroku create <Nome da aplicação>
heroku addons:add heroku-postgresql:hobby-dev

heroku pg:psql DATABASE_URL --app <Nome da aplicação>
```

* No seu *banco de dados* do *Heroku*, execute os comandos SQL dos arquivos **casa_das_quentinhas-psql-ddl.sql**, **uf-dml.sql**, **cidade-ddd31-dml.sql**, **usuario-dml.sql**, **empresa-dml.sql**, **funcionario-dml.sql** e **cliente-dml.sql** na ordem que foram informados. Esses arquivos estão localizados no diretório *database*.
* Em seguida, feche o *banco de dados* e execute a linha abaixo:	 
```sh
git push heroku -u master
```

* No **Windows**, abra um navegador e informe o endereço da sua aplicação no *Heroku*.
* Para logar na aplicação, a senha é **12345** e os e-mails de acesso são: **gerente@casa-das-quentinhas.com**, **atendente@casa-das-quentinhas.com**, **entregador@casa-das-quentinhas.com**, **empresa@casa-das-quentinhas.com** e **cliente@casa-das-quentinhas.com**.

* Caso deseje remover a aplicação do seu *PAAS*, execute a linha abaixo:
```sh		
heroku apps:destroy --app <Nome da aplicação>
```