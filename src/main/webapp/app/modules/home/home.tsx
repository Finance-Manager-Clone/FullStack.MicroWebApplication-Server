import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { Row, Col, Alert } from 'reactstrap';

import { useAppSelector } from 'app/config/store';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <div className = "backgroundArea">
    <Row>
      <Col md="3" className="pad">
        <span className = "graph"></span>
      </Col>
      <Col md="9">
        <h2>
          <Translate contentKey="home.title">Welcome, Zip Bankers</Translate>
        </h2>
        <p className="lead">
          <Translate contentKey="home.subtitle"></Translate>
        </p>
        {account?.login ? (
          <div>
            <Alert color="success">
              <Translate contentKey="home.logged.message" interpolate={{ username: account.login }}>
                {account.login} is logged in.
              </Translate>
            </Alert>
          </div>
        ) : (
          <div className = "signInArea">
            <form action="action_page.php" method="post">
              <div className="container">
                  <label htmlFor="uname"><b>Username</b></label>
                  <input type="text" placeholder="Enter Username" name="uname" required></input>

                  <label htmlFor="psw"><b>Password</b></label>
                  <input type="password" placeholder="Enter Password" name="psw" required></input>

                  <button type="submit">Login</button>
                </div>
            </form>

            <Alert>
              <Translate contentKey="global.messages.info.authenticated.prefix">If you want to </Translate>

              <Link to="/login" className="alert-link">
                <Translate contentKey="global.messages.info.authenticated.link"> sign in</Translate>
              </Link>
              <Translate contentKey="global.messages.info.authenticated.suffix">
                , you can try the default accounts:
                <br />- Administrator (login=&quot;admin&quot; and password=&quot;admin&quot;)
                <br />- User (login=&quot;user&quot; and password=&quot;user&quot;).
              </Translate>
            </Alert>

            <Alert>
              <Translate contentKey="global.messages.info.register.noaccount">You do not have an account yet?</Translate>&nbsp;
              <Link to="/account/register" className="alert-link">
                <Translate contentKey="global.messages.info.register.link">Create new account</Translate>
              </Link>
            </Alert>
          </div>
        )}
      </Col>
    </Row>
    </div>
  );
};

export default Home;
