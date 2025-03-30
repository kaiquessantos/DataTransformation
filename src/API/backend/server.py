from flask import Flask, request, jsonify
import pandas as pd

app = Flask(__name__)

# Carregar o CSV de operadoras ativas
df = pd.read_csv('operadoras_ativas.csv', sep=';', encoding='utf-8')

@app.route('/busca', methods=['GET'])
def buscar_operadoras():
    consulta = request.args.get('q', '').lower()
    # Filtrar operadoras por razão social ou nome fantasia
    resultados = df[
        df['Razao_Social'].str.lower().str.contains(consulta, na=False) |
        df['Nome_Fantasia'].str.lower().str.contains(consulta, na=False)
    ]
    # Retornar os resultados como JSON
    return jsonify(resultados.to_dict('records'))

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=5000)