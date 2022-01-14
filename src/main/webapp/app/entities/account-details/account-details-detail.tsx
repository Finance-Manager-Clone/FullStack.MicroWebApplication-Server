import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './account-details.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AccountDetailsDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const accountDetailsEntity = useAppSelector(state => state.accountDetails.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="accountDetailsDetailsHeading">
          <Translate contentKey="myApp.accountDetails.detail.title">AccountDetails</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{accountDetailsEntity.id}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="myApp.accountDetails.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{accountDetailsEntity.firstName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="myApp.accountDetails.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{accountDetailsEntity.lastName}</dd>
          <dt>
            <span id="phoneNumber">
              <Translate contentKey="myApp.accountDetails.phoneNumber">Phone Number</Translate>
            </span>
          </dt>
          <dd>{accountDetailsEntity.phoneNumber}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="myApp.accountDetails.email">Email</Translate>
            </span>
          </dt>
          <dd>{accountDetailsEntity.email}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="myApp.accountDetails.address">Address</Translate>
            </span>
          </dt>
          <dd>{accountDetailsEntity.address}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="myApp.accountDetails.city">City</Translate>
            </span>
          </dt>
          <dd>{accountDetailsEntity.city}</dd>
          <dt>
            <span id="country">
              <Translate contentKey="myApp.accountDetails.country">Country</Translate>
            </span>
          </dt>
          <dd>{accountDetailsEntity.country}</dd>
          <dt>
            <Translate contentKey="myApp.accountDetails.user">User</Translate>
          </dt>
          <dd>{accountDetailsEntity.user ? accountDetailsEntity.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/account-details" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/account-details/${accountDetailsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AccountDetailsDetail;
