import { useState } from "react";
import { Button, Col, Form, Row } from "react-bootstrap";
import apiClient from "../api/axios";
import { useNavigate } from "react-router-dom";

function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState(null);

  const navigate = useNavigate();

  function createUser() {
    navigate("/create_user");
  }

  const handleLogin = async () => {
  try {
    const response = await apiClient.post("/users/login", {
      username,
      password
    });
    if (response.data.token != null) {
      localStorage.setItem("jsonwebtoken", response.data.token);
      navigate("/home");
    } else {
      setErrorMessage("Credentiale incorecte.");
    }
  } catch(error) {
    setErrorMessage("Credentiale incorecte.");
  } 
  };

  return (
    <div className="login">
      <Form className="form">
        <h1>Intra in contul tau</h1>
        {errorMessage && <div style={{color: 'red'}}>{errorMessage}</div>}
        <Form.Group as={Row} className="mb-3">
          <Form.Label column sm="2">
            Email
          </Form.Label>
          <Col sm="10">
            <Form.Control
              type="email"
              placeholder="email@example.com"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
            />
          </Col>
        </Form.Group>

        <Form.Group as={Row} className="mb-3">
          <Form.Label column sm="2">
            Parola
          </Form.Label>
          <Col sm="10">
            <Form.Control
              type="password"
              placeholder="Parola"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </Col>
        </Form.Group>
        <Button onClick={handleLogin} variant="secondary" size="lg">Intra in cont</Button> 
      </Form>
      <div className="cont">
      <h3>Nu ai cont?</h3>
        <Button onClick={createUser} variant="secondary" size="lg">Creeaza unul aici</Button>
        </div>
    </div>
  );
}

export default Login;
