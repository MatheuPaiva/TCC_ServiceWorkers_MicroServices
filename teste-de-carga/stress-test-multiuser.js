import http from 'k6/http';
import { check, sleep } from 'k6';

// CONFIGURAÇÃO DO CENÁRIO
export const options = {
  scenarios: {
    rajada_de_conexoes: {
      executor: 'per-vu-iterations',
      vus: 50,              // 50 Usuários simultâneos
      iterations: 50,        // Cada um envia 5 relatórios
      maxDuration: '1m',    // Tempo máximo total
    },
  },
  thresholds: {
    http_req_duration: ['p(95)<2000'], // 95% das requisições devem ser < 2s
    http_req_failed: ['rate<0.01'],    // Menos de 1% de erro
  },
};


let authToken = null;


export default function () {
  
  
  if (!authToken) {
    // __VU é uma variável global que retorna o ID do usuário (1 a 50)
    const email = `tecnico${__VU}@siga.com`;
    
    const loginRes = http.post('http://localhost:9000/api/auth/login', JSON.stringify({
      email: email,
      senha: '123456'
    }), {
      headers: { 'Content-Type': 'application/json' },
    });

    
    if (loginRes.status !== 200) {
      console.error(`Erro login VU ${__VU} (${email}): ${loginRes.status} - ${loginRes.body}`);
      return; 
    }
    
    authToken = loginRes.json('token');
  }

  
  const payload = JSON.stringify({
    ownerName: `Produtor Teste VU-${__VU}`,
    propertyName: `Fazenda Stress Test ${__ITER}`,
    document: "12345678900",
    totalArea: 100 + __ITER,
    cropType: "Soja",
    developmentStage: "Colheita",
    plantingDensity: 5000,
    cultivationMethod: "Direto",
    latitude: "-24.123",
    longitude: "-54.123",
    encryptedData: "DADOS_SIMULADOS_CRIPTOGRAFADOS_XYZ"
  });

  const params = {
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${authToken}`, // Usa o token obtido no login
    },
  };

  const res = http.post('http://localhost:9000/api/relatorios', payload, params);

  // 3. VERIFICAÇÃO
  check(res, {
    'Status 200 OK': (r) => r.status === 200,
  });

  
  sleep(Math.random() + 0.5);
}