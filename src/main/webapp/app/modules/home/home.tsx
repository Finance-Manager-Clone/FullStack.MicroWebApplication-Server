import './home.scss';

import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Translate, translate, ValidatedField } from 'react-jhipster';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter, Alert, Row, Col, Form } from 'reactstrap';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { useForm } from 'react-hook-form';
import { Redirect, RouteComponentProps } from 'react-router-dom';
import LoginModal from '../login/login-modal';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <div className = "backgroundArea">
    <Row>
      <Col md="3" className="pad">
        <span className = "logo"></span>
      </Col>
      <Col className = "mainContent" md="9">
        {account?.login ? (
          <div>
            <h2>
              <Translate contentKey="home.title">Welcome, Zip Bankers</Translate>
            </h2>
            
            <p className="lead">
              <Translate contentKey="home.subtitle"></Translate>
            </p>

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
            <Link to="/login" className="alert-link">
              <Translate contentKey="global.messages.info.authenticated.link"> sign in</Translate>
            </Link>
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
