import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './transaction.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TransactionDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const transactionEntity = useAppSelector(state => state.transaction.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="transactionDetailsHeading">
          <Translate contentKey="myApp.transaction.detail.title">Transaction</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{transactionEntity.id}</dd>
          <dt>
            <span id="transactionType">
              <Translate contentKey="myApp.transaction.transactionType">Transaction Type</Translate>
            </span>
          </dt>
          <dd>{transactionEntity.transactionType}</dd>
          <dt>
            <span id="amount">
              <Translate contentKey="myApp.transaction.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{transactionEntity.amount}</dd>
          <dt>
            <span id="time">
              <Translate contentKey="myApp.transaction.time">Time</Translate>
            </span>
          </dt>
          <dd>{transactionEntity.time ? <TextFormat value={transactionEntity.time} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="currency">
              <Translate contentKey="myApp.transaction.currency">Currency</Translate>
            </span>
          </dt>
          <dd>{transactionEntity.currency}</dd>
          <dt>
            <Translate contentKey="myApp.transaction.category">Category</Translate>
          </dt>
          <dd>{transactionEntity.category ? transactionEntity.category.categoryName : ''}</dd>
          <dt>
            <Translate contentKey="myApp.transaction.from">From</Translate>
          </dt>
          <dd>{transactionEntity.from ? transactionEntity.from.accountNumber : ''}</dd>
          <dt>
            <Translate contentKey="myApp.transaction.to">To</Translate>
          </dt>
          <dd>{transactionEntity.to ? transactionEntity.to.accountNumber : ''}</dd>
          <dt>
            <Translate contentKey="myApp.transaction.user">User</Translate>
          </dt>
          <dd>{transactionEntity.user ? transactionEntity.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/transaction" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/transaction/${transactionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TransactionDetail;
