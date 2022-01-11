import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './product-account.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ProductAccountDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const productAccountEntity = useAppSelector(state => state.productAccount.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productAccountDetailsHeading">
          <Translate contentKey="myApp.productAccount.detail.title">ProductAccount</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{productAccountEntity.id}</dd>
          <dt>
            <span id="accountNumber">
              <Translate contentKey="myApp.productAccount.accountNumber">Account Number</Translate>
            </span>
          </dt>
          <dd>{productAccountEntity.accountNumber}</dd>
          <dt>
            <span id="accounType">
              <Translate contentKey="myApp.productAccount.accounType">Accoun Type</Translate>
            </span>
          </dt>
          <dd>{productAccountEntity.accounType}</dd>
          <dt>
            <span id="openingDate">
              <Translate contentKey="myApp.productAccount.openingDate">Opening Date</Translate>
            </span>
          </dt>
          <dd>
            {productAccountEntity.openingDate ? (
              <TextFormat value={productAccountEntity.openingDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="balance">
              <Translate contentKey="myApp.productAccount.balance">Balance</Translate>
            </span>
          </dt>
          <dd>{productAccountEntity.balance}</dd>
          <dt>
            <Translate contentKey="myApp.productAccount.accountDetails">Account Details</Translate>
          </dt>
          <dd>{productAccountEntity.accountDetails ? productAccountEntity.accountDetails.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/product-account" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/product-account/${productAccountEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProductAccountDetail;
