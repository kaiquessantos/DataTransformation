<body>
    <h1>Projeto: Extração, Transformação e Busca de Dados de Operadoras de Saúde</h1>
    <p>Este projeto é composto por scripts em Java, Python e uma aplicação web (Flask + Vue.js) que realizam as seguintes tarefas:</p>
    <ol>
        <li><strong>Web Scraping</strong>: Baixa PDFs de um site governamental brasileiro e os compacta em um arquivo ZIP.</li>
        <li><strong>Transformação de Dados</strong>: Extrai informações de um PDF e as converte em um arquivo CSV, compactando-o posteriormente.</li>
        <li><strong>Preparação de Banco de Dados</strong>: Faz o download e descompacta arquivos ZIP de demonstrações contábeis e baixa um CSV de operadoras ativas.</li>
        <li><strong>API e Frontend</strong>: Cria uma API em Flask para buscar operadoras de saúde ativas e um frontend em Vue.js para interação com o usuário.</li>
    </ol>
    <p>O objetivo é automatizar a coleta, transformação e consulta de dados relacionados a operadoras de saúde no Brasil, utilizando fontes oficiais da ANS (Agência Nacional de Saúde Suplementar).</p>

  <h2>Estrutura do Projeto</h2>
    <ul>
        <li><code>WebScrapingTest.java</code>: Script Java para scraping de PDFs do site da ANS.</li>
        <li><code>DataTransformationTest.java</code>: Script Java para transformar PDFs em CSV.</li>
        <li><code>DatabasePreparation.java</code>: Script Java para baixar e preparar arquivos de demonstrações contábeis e operadoras ativas.</li>
        <li><code>app.py</code>: API em Flask para buscar operadoras ativas.</li>
        <li><code>App.vue</code>: Frontend em Vue.js para interagir com a API.</li>
        <li><code>postman_collection.json</code>: Coleção Postman para testar a API.</li>
    </ul>

  <h2>Pré-requisitos</h2>
    <p>Para executar este projeto, você precisará instalar as seguintes ferramentas e bibliotecas:</p>

  <h3>Java</h3>
    <ul>
        <li><strong>JDK 17 ou superior</strong>: Necessário para executar os scripts Java.</li>
        <li><strong>Maven</strong>: Para gerenciar as dependências Java.</li>
    </ul>

  <h3>Python</h3>
    <ul>
        <li><strong>Python 3.8 ou superior</strong>: Necessário para a API Flask.</li>
        <li><strong>Pip</strong>: Gerenciador de pacotes Python.</li>
    </ul>

  <h3>Node.js</h3>
    <ul>
        <li><strong>Node.js 18 ou superior</strong>: Necessário para o frontend Vue.js.</li>
        <li><strong>npm</strong>: Gerenciador de pacotes Node.js.</li>
    </ul>

  <h3>Bibliotecas Específicas</h3>
    <p>Abaixo estão as bibliotecas utilizadas e como instalá-las.</p>

  <h4>Dependências Java (Maven)</h4>
    <p>Adicione as seguintes dependências ao arquivo <code>pom.xml</code>:</p>
    <pre>
&lt;dependencies&gt;
    &lt;!-- Jsoup para web scraping --&gt;
    &lt;dependency&gt;
        &lt;groupId&gt;org.jsoup&lt;/groupId&gt;
        &lt;artifactId&gt;jsoup&lt;/artifactId&gt;
        &lt;version&gt;1.17.2&lt;/version&gt;
    &lt;/dependency&gt;
    &lt;!-- PDFBox para manipulação de PDFs --&gt;
    &lt;dependency&gt;
        &lt;groupId&gt;org.apache.pdfbox&lt;/groupId&gt;
        &lt;artifactId&gt;pdfbox&lt;/artifactId&gt;
        &lt;version&gt;2.0.27&lt;/version&gt;
    &lt;/dependency&gt;
    &lt;!-- OpenCSV para geração de CSV --&gt;
    &lt;dependency&gt;
        &lt;groupId&gt;com.opencsv&lt;/groupId&gt;
        &lt;artifactId&gt;opencsv&lt;/artifactId&gt;
        &lt;version&gt;5.9&lt;/version&gt;
    &lt;/dependency&gt;
&lt;/dependencies&gt;
    </pre>
    <p>Para instalar, execute:</p>
    <pre><code>mvn install</code></pre>

  <h4>Dependências Python (Pip)</h4>
    <p>Instale as dependências Python com o seguinte comando:</p>
    <pre><code>pip install flask pandas</code></pre>

  <h4>Dependências Vue.js (npm)</h4>
    <p>No diretório do frontend (<code>App.vue</code>), instale as dependências:</p>
    <pre><code>npm install axios vue</code></pre>

  <h2>Uso Detalhado</h2>

<h3>1. WebScrapingTest.java</h3>
    <p><strong>Propósito</strong>: Faz o download de PDFs específicos (Anexo I e Anexo II) de um site da ANS e os compacta em um arquivo ZIP.</p>
    <p><strong>Como Executar</strong>:</p>
    <ol>
        <li>Certifique-se de que o <code>pom.xml</code> contém as dependências necessárias.</li>
        <li>Compile e execute o script:
            <pre><code>mvn compile exec:java -Dexec.mainClass="kaique.WebScrapingTest"</code></pre>
        </li>
        <li><strong>Saída</strong>: Dois PDFs (<code>Anexo_I_*.pdf</code> e <code>Anexo_II_*.pdf</code>) serão baixados e compactados em <code>Anexos.zip</code>.</li>
    </ol>

  <h3>2. DataTransformationTest.java</h3>
    <p><strong>Propósito</strong>: Lê o PDF <code>Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf</code>, extrai informações sobre procedimentos médicos e as salva em um CSV (<code>rol_procedimentos.csv</code>), que é então compactado em <code>Teste_Kaique.zip</code>.</p>
    <p><strong>Como Executar</strong>:</p>
    <ol>
        <li>Certifique-se de que o PDF especificado está no diretório atual.</li>
        <li>Compile e execute:
            <pre><code>mvn compile exec:java -Dexec.mainClass="DataTransformationTest"</code></pre>
        </li>
        <li><strong>Saída</strong>: Um arquivo <code>rol_procedimentos.csv</code> e um <code>Teste_Kaique.zip</code> contendo o CSV.</li>
    </ol>

  <h3>3. DatabasePreparation.java</h3>
    <p><strong>Propósito</strong>: Baixa arquivos ZIP de demonstrações contábeis da ANS (2023 e 2024), os descompacta e baixa um CSV de operadoras ativas.</p>
    <p><strong>Como Executar</strong>:</p>
    <ol>
        <li>Compile e execute:
            <pre><code>mvn compile exec:java -Dexec.mainClass="kaique.DatabasePreparation"</code></pre>
        </li>
        <li><strong>Saída</strong>: Arquivos descompactados de cada ZIP e o arquivo <code>operadoras_ativas.csv</code>.</li>
    </ol>

  <h3>4. API Flask (server.py)</h3>
    <p><strong>Propósito</strong>: Cria uma API que permite buscar operadoras de saúde ativas com base em um termo de consulta.</p>
    <p><strong>Como Executar</strong>:</p>
    <ol>
        <li>Certifique-se de que o arquivo <code>operadoras_ativas.csv</code> está no mesmo diretório.</li>
        <li>Execute o script:
            <pre><code>python server.py</code></pre>
        </li>
        <li>Acesse a API em <code>http://localhost:5000/busca?q=&lt;termo&gt;</code>.</li>
    </ol>
    <p><strong>Exemplo</strong>: <code>http://localhost:5000/busca?q=unimed</code> retorna operadoras com "unimed" no nome.</p>

  <h3>5. Frontend Vue.js (App.vue)</h3>
    <p><strong>Propósito</strong>: Interface web para buscar operadoras via API.</p>
    <p><strong>Como Executar</strong>:</p>
    <ol>
        <li>No diretório do frontend, instale as dependências:
            <pre><code>npm install</code></pre>
        </li>
        <li>Inicie o servidor de desenvolvimento Vue:
            <pre><code>npm run serve</code></pre>
        </li>
        <li>Acesse <code>http://localhost:8080</code> no navegador.</li>
    </ol>
    <p><strong>Uso</strong>: Digite um termo no campo de busca para listar operadoras correspondentes.</p>

  <h3>6. Teste com Postman</h3>
    <p><strong>Propósito</strong>: Testar a API Flask usando a coleção fornecida.</p>
    <p><strong>Como Usar</strong>:</p>
    <ol>
        <li>Importe o arquivo <code>postman_collection.json</code> no Postman.</li>
        <li>Configure a variável <code>consulta</code> com um termo (ex.: "unimed").</li>
        <li>Execute a requisição "Buscar Operadoras".</li>
    </ol>

  <h2>Notas Adicionais</h2>
    <ul>
        <li><strong>Diretórios</strong>: Certifique-se de que os arquivos baixados e gerados têm permissões de escrita no diretório atual.</li>
        <li><strong>Erros</strong>: Verifique as mensagens de erro no console para problemas de conexão ou arquivos ausentes.</li>
        <li><strong>Personalização</strong>: Ajuste URLs e nomes de arquivos conforme necessário.</li>
    </ul>
</body>
